package com.medicalproject.util;

import com.medicalproject.bean.DoctorBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 * 数据库工具类
 * @author 汪文博
 */
public class DBUtils {

    //MySQL驱动
    private static String driver = "org.gjt.mm.mysql.Driver";
    //用户名
    private static String user = "root";
    //密码
    private static String password = "mysqladmin";

    private static Connection getConn(String dbName){

        Connection connection = null;
        try{
            Class.forName(driver);    //动态加载类
            String ip = "192.168.43.31";      //本机IP地址，每次更换网路都需做修改！！！

            //尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/"+dbName, user, password);
        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 根据科室名称获取医生信息
     * @param officeName 科室名称
     * @return 医生信息
     */
    public static List<DoctorBean> getDoctorInfoByOfficeName(String officeName){
        List<DoctorBean> list = new ArrayList<>();
        //根据数据库名称，建立连接
        Connection connection = getConn("medical");

        try{
            String sql = "select * from doctor where DoctorOffice = ?";
            if(connection != null){    //成功与数据库建立连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if(ps != null){
                    ps.setString(1, officeName);
                    //执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    if(rs != null){     //查询结果不为空
                        while(rs.next()){
                            String doctorName = rs.getString(2);
                            String doctorIntro = rs.getString(3);
                            DoctorBean doctorBean = new DoctorBean(doctorName, doctorIntro);
                            list.add(doctorBean);
                        }
                        rs.close();
                        ps.close();
                        connection.close();
                        return list;
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
