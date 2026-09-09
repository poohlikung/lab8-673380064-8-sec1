# Lab 8 — Product Shop

นายสรวิชญ์ วันเสน รหัสนักศึกษา 673380064-8 Sec.1

## ความสามารถ

- แสดงรายการสินค้า
- เพิ่ม แก้ไข และลบสินค้า
- เก็บรายละเอียดสินค้าแบบความสัมพันธ์ 1:1
- เก็บรีวิวสินค้าแบบความสัมพันธ์ 1:N
- คำนวณราคาหลังส่วนลดด้วย Strategy Pattern

## เทคโนโลยีที่ใช้

- Java 17+
- Spring Boot 4.1.1
- Spring MVC และ Thymeleaf
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven Wrapper

## สิ่งที่ต้องติดตั้ง

1. JDK 17 ขึ้นไป
2. PostgreSQL

ไม่จำเป็นต้องติดตั้ง Maven แยก เพราะโปรเจกต์มี `mvnw` และ `mvnw.cmd` อยู่แล้ว

## ตั้งค่าฐานข้อมูล

สร้างฐานข้อมูลชื่อ `lab8` ใน PostgreSQL และตรวจสอบค่าที่ไฟล์ `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lab8
spring.datasource.username=postgres
spring.datasource.password=123456789za
```

หากรหัสผ่าน PostgreSQL ไม่ใช่ค่านี้ ให้แก้ `spring.datasource.password` ให้ตรงกับรหัสของผู้ใช้ `postgres`

## วิธีรันโปรเจกต์

Linux/macOS:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

Windows:

```bat
mvnw.cmd spring-boot:run
```

จากนั้นเปิดเว็บไซต์ที่ http://localhost:8081/products

## URL หลัก

| Method | URL | รายละเอียด |
|---|---|---|
| GET | `/products` | แสดงรายการสินค้า |
| GET | `/products/add` | แสดงฟอร์มเพิ่มสินค้า |
| POST | `/products/save` | บันทึกสินค้า |
| GET | `/products/edit/{id}` | แสดงฟอร์มแก้ไข |
| POST | `/products/update/{id}` | บันทึกการแก้ไข |
| GET | `/products/delete/{id}` | แสดงหน้ายืนยันการลบ |
| POST | `/products/delete/{id}` | ลบสินค้า |

## โครงสร้างสำคัญ

- `controller/ProductController` รับ HTTP Request และเลือกหน้าเว็บ
- `service/ProductService` จัดการ Business Logic
- `repository` ติดต่อฐานข้อมูลผ่าน Spring Data JPA
- `model` ประกอบด้วย `Product`, `ProductDetail` และ `Review`
- `strategy` ประกอบด้วยกลยุทธ์การคำนวณส่วนลด
- `templates/products` เก็บหน้า HTML ของ Thymeleaf
- `static/style.css` เก็บไฟล์ CSS

## ความสัมพันธ์ของ Entity

- `Product` กับ `ProductDetail` เป็นแบบ **1:1** สินค้าหนึ่งรายการมีรายละเอียดหนึ่งชุด
- `Product` กับ `Review` เป็นแบบ **1:N** สินค้าหนึ่งรายการมีรีวิวได้หลายรายการ

## Strategy Pattern

ระบบใช้ `DiscountStrategy` เป็น interface กลาง และแยกการคำนวณส่วนลดเป็นคลาส ได้แก่ `NoDiscountStrategy`, `MemberDiscountStrategy` และ `SeasonalSaleStrategy` หากต้องการเพิ่มส่วนลดแบบใหม่ สามารถสร้าง Strategy class ใหม่ได้โดยไม่ต้องรวมโค้ดส่วนลดทุกแบบไว้ในคลาสเดียว

