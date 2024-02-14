package ru.petrov.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import ru.petrov.InitializationDb;
import ru.petrov.annotations.Loggable;
import ru.petrov.controller.UserController;
import ru.petrov.dto.UserDto;
import ru.petrov.dto.UserInDto;
import ru.petrov.model.Log;
import ru.petrov.model.LogLevel;
import ru.petrov.repository.LogRepository;
import ru.petrov.repository.jdbc.JdbcLogRepository;
import ru.petrov.repository.jdbc.JdbcUserRepository;
import ru.petrov.util.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private UserController userController;
    private LogRepository logRepository;
    private JdbcUserRepository jdbcUserRepository;


    @Override
    public void init() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jdbcUserRepository = new JdbcUserRepository();
        userController = new UserController(new ModelMapper(), jdbcUserRepository);
        logRepository = new JdbcLogRepository(jdbcUserRepository);
        InitializationDb.migration();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String input = convertToString(req.getReader());
        final JSONObject jsonObject = doWork(input);
        UserInDto userInDto = objectMapper.readValue(jsonObject.toString(), UserInDto.class);
        UserDto saveUser = userController.save(userInDto);
        if (saveUser != null) {
            System.out.println(req.getContextPath());
            System.out.println(req.getRequestURL());
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.sendRedirect(req.getContextPath() + "/user/"+saveUser.getId());
        } else {
            logRepository.save(new Log(LogLevel.WARN,
                    jdbcUserRepository.get("service")
                    .orElseThrow(() -> new NotFoundException("Not found entity with name: service")),
                    "Произошла ошибка при добавлении пользователя " + userInDto.toString()));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("something wrong ");
        }
    }

    @Loggable
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(req.getRequestURI());
        if (req.getRequestURI().startsWith(req.getContextPath() + "/")) {
            String stringId = req.getRequestURI().substring(req.getRequestURI().lastIndexOf('/') + 1);
            if (stringId.equals("")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("missing parameter id ");
                //TODO перенести в лог
                System.out.println("bad url: " + req.getRequestURI());
            } else {
                int id = 0;
                try {
                    id = Integer.parseInt(stringId);
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().println("missing or not correct parameter id ");
                    //TODO перенести в лог
                    System.out.println("bad url: " + req.getRequestURI());
                }
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application.json");
                if (userController.get(id) != null) {
                    byte[] bytes = objectMapper.writeValueAsBytes(userController.get(id));
                    resp.getOutputStream().write(bytes);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println("user with id=" + stringId + " not found");
                }
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("missing or not correct parameter id ");
            //TODO перенести в лог
            System.out.println("bad url: " + req.getRequestURI());
        }
    }

    public JSONObject doWork(String output) {
        return new JSONObject(output);
    }

    public String convertToString(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString().replace("[", "").replace("]", "");
    }
}
