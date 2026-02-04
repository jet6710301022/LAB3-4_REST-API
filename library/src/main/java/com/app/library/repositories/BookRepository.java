package com.app.library.repositories;

import com.app.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // บอกให้ Spring ทราบว่านี่คือตัวจัดการข้อมูลใน Database
public interface BookRepository extends JpaRepository<Book, Long> {
    // JpaRepository จะสร้างคำสั่ง Save, Find, Delete ให้เราโดยอัตโนมัติครับ
}