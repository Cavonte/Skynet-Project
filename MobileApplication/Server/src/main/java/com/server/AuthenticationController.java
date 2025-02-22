package com.server;

import Core.Student;
import Mapper.StudentMapper;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class AuthenticationController {

    @RequestMapping(value = "/login", method = RequestMethod.GET,produces = "application/json")
    public boolean login(@RequestParam(value="username", defaultValue="") String username, @RequestParam(value="password", defaultValue="") String password) throws ClassNotFoundException, SQLException
    {
        if(!areValidFormat(username, password))
            return false;

        password = Student.encryptPassword(password);

        //if log in info correct
        int studentId = Integer.parseInt(username);
        Student student = StudentMapper.getData(studentId);
        if(!isValidPassword(student, password))
            return false;

        //SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
        return true;
    }

    /**
     * Method to before basic validation on the length of the parameters, in case the front end did not
     * @param username
     * @param password
     * @return true if it validates properly
     */
    private boolean areValidFormat(String username, String password)
    {
        return Student.isValidUsername(username) && Student.isValidPassword(password);
    }

    private boolean isValidPassword(Student student, String password)
    {
        if(student == null || !password.equals(student.getPassword()))
            return false;
        return true;
    }
}