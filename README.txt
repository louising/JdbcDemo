-------------------------------
cd ../JdbcDemo
git init
git remote add origin git@github.com:louising/JdbcDemo.git
git checkout -b dev
-------------------------------


//Start DB (H2 DB)
c:\>java -jar "H:/lib/java/h2-1.4.197.jar"   
URL: jdbc:h2:tcp://localhost/~/H2DB-SpringRestDemo
user: sa
password: sa
           
DROP TABLE if exists t_user;
CREATE TABLE t_user(
    user_id INT IDENTITY,
    user_name VARCHAR(20), 
    login_name VARCHAR(20), 
    email VARCHAR(30), 
    birth_date DATE DEFAULT CURRENT_DATE,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
