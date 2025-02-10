package com.lucas.bootbasic.modules.dip;

/**
 * @package : com.lucas.bootbasic.dip
 * @name : SampleRepository.java
 * @date : 2025. 1. 15. 오후 6:03
 * @author : lucaskang(swings134man)
 * @Description: Sample Repository Interface
**/
public interface SampleRepository {
    void save(String msg);
    String get(String msg);
}
