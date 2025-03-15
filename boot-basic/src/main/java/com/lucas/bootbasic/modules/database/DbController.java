package com.lucas.bootbasic.modules.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @package : com.lucas.bootbasic.modules.database
 * @name : DbController.java
 * @date : 2025. 3. 15. 오후 4:28
 * @author : lucaskang(swings134man)
 * @Description: Hikari DB Connection 에 관한 정보를 확인하기 위한 Controller
**/
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/db")
public class DbController {

    private final DataSource dataSource;

    @GetMapping("/status")
    public Map<String, Object> connectionStatusCheck() {
        if (dataSource instanceof HikariDataSource hikariDataSource) {
            Map<String, Object> poolStatus = new HashMap<>();
            poolStatus.put("activeConnections", hikariDataSource.getHikariPoolMXBean().getActiveConnections());                 // Connection Pool Active Count
            poolStatus.put("idleConnections", hikariDataSource.getHikariPoolMXBean().getIdleConnections());                     // Connection Pool Idle Count
            poolStatus.put("totalConnections", hikariDataSource.getHikariPoolMXBean().getTotalConnections());                   // Total Connection Count
            poolStatus.put("threadsAwaitingConnection", hikariDataSource.getHikariPoolMXBean().getThreadsAwaitingConnection()); // Connection Waiting Thread Count
            return poolStatus;
        }
        throw new IllegalStateException("DataSource is not an instance of HikariDataSource");
    }
}
