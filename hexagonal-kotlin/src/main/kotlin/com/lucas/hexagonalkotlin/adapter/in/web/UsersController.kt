package com.lucas.hexagonalkotlin.adapter.`in`.web

import com.lucas.hexagonalkotlin.application.users.commands.UserCommandMapper
import com.lucas.hexagonalkotlin.application.users.port.`in`.UsersUseCase
import com.lucas.hexagonalkotlin.domain.users.dto.UserSaveDto
import com.lucas.hexagonalkotlin.domain.users.dto.UserUpdateDto
import com.lucas.hexagonalkotlin.domain.users.dto.UsersDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * UsersController.kt: Web In Adapter - Users API Controller
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:03
 * @description: 
 */
@RestController
@RequestMapping("/api/users")
class UsersController(
    private val usersUseCase: UsersUseCase,
    private val userMapper: UserCommandMapper
) {

    @PostMapping
    fun createUser(@RequestBody dto: UserSaveDto): ResponseEntity<UsersDto> {
        val result = usersUseCase.createUser(userMapper.toCreateCommand(dto))
        return ResponseEntity.ok(result)
    }

    @PutMapping
    fun updateUser(@RequestBody dto: UserUpdateDto): ResponseEntity<UsersDto> {
        val result = usersUseCase.updateUser(userMapper.toUpdateCommand(dto))
        return ResponseEntity.ok(result)
    }

}