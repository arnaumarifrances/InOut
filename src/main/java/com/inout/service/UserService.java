package com.inout.service;

import com.inout.dto.RegisterEmployeeDTO;
import com.inout.dto.UserDTO;

public interface UserService {
    UserDTO registerEmployee(RegisterEmployeeDTO dto);
}
