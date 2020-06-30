package cn.edu.sdut.test.user;

import cn.edu.sdut.entity.Tbuser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestUser {
    private String mybatisconfig="mybatis.xml";
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;

    @Before
    public void init(){
        try {
            InputStream inputStream= Resources.getResourceAsStream(mybatisconfig);
            this.sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
            this.session=this.sqlSessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @After
    public void destory(){
        session.commit();
        session.close();
    }

    @Test
    public void testQueryUserById(){
        int userid=3;
        Tbuser user=session.selectOne("ywh.queryUserById",userid);
        System.out.println("tbuser = " + user);
    }

    private static final String NAMESPACE="ywh.";//变量名必须大写
    @Test
    public void testQueryUserCount(){
        int row=session.selectOne(NAMESPACE+"queryUserCount");
        System.out.println("row = " + row);
    }
    @Test
    public void testQueryUserByUsername(){
        String username="admin";
        Tbuser tbuser=session.selectOne(NAMESPACE+"queryUserByUsername",username);
        System.out.println("tbuser = " + tbuser);
    }
    @Test
    public void testLogin(){
        Tbuser tbuser=new Tbuser("admin","1234");
        Tbuser u=session.selectOne(NAMESPACE+"login",tbuser);
        System.out.println("u="+u);
    }
    @Test
    public void testQueryUsers(){
        List<Tbuser> uers=this.session.selectList(NAMESPACE+"queryUsers");
        uers.forEach(u->System.out.println(u));//注意这种写法
    }
    private void print(int row){
        if(row>0){
            System.out.println("操作成功");
        }else{
            System.out.println("操作失败");
        }
    }
    @Test
    public void testAddUser(){
        Tbuser tbuser=new Tbuser(0,"doudou","66666","02","小宝贝",null);
        this.print(this.session.insert(NAMESPACE+"addUser",tbuser));
    }
    @Test
    public void testUpdateUser(){
        Tbuser tbuser=new Tbuser(10,"杨文迪","22345","02","改昵称了",null);
        this.print(this.session.update(NAMESPACE+"updateUser",tbuser));
    }
    @Test
    public void testDeleteUser(){
        int userid=10;
        this.print(this.session.delete(NAMESPACE+"deleteUser",userid));
    }

}
