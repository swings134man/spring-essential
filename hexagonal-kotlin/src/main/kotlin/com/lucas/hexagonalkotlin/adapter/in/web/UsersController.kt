package com.lucas.hexagonalkotlin.adapter.`in`.web

import com.lucas.hexagonalkotlin.application.users.mapper.UserCommandMapper
import com.lucas.hexagonalkotlin.application.users.port.`in`.UsersUseCase
import com.lucas.hexagonalkotlin.domain.users.dto.UserPasswordUpdateDto
import com.lucas.hexagonalkotlin.domain.users.dto.UserPhoneNumberUpdateDto
import com.lucas.hexagonalkotlin.domain.users.dto.UserSaveDto
import com.lucas.hexagonalkotlin.domain.users.dto.UserUpdateDto
import com.lucas.hexagonalkotlin.domain.users.dto.UsersDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping
    fun findAll(): ResponseEntity<List<UsersDto>?> {
        val result = usersUseCase.findAllUsers()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<UsersDto?> {
        val result = usersUseCase.findUserById(id)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/password")
    fun changePassword(@RequestBody dto: UserPasswordUpdateDto): ResponseEntity<String> {
        val result = usersUseCase.updateUserPassword(userMapper.toUpdatePasswordCommand(dto))
        return ResponseEntity.ok("Password updated successfully")
    }

    @PostMapping("/update-phone")
    fun changePhoneNumber(@RequestBody dto: UserPhoneNumberUpdateDto): ResponseEntity<UsersDto> {
        val result = usersUseCase.updateUserPhoneNumber(userMapper.toUserWithPhoneNumberCommand(dto))
        return ResponseEntity.ok(result)
    }

    @PostMapping("/deactivate/{id}")
    fun deactivateUser(@PathVariable id: Long): ResponseEntity<String> {
        usersUseCase.deactivateUser(id)
        return ResponseEntity.ok("deactivated")
    }

    @PostMapping("/activate")
    fun activateUser(@RequestBody dto: UserPhoneNumberUpdateDto): ResponseEntity<String> {
        val result = usersUseCase.activateUser(userMapper.toUserWithPhoneNumberCommand(dto))
        return ResponseEntity.ok(if(result.isActive) "verified" else "not verified")
    }

}