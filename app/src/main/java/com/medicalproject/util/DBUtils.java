package com.medicalproject.util;

import com.medicalproject.bean.DoctorBean;
import com.medicalproject.bean.ReportBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

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
            String ip = "192.168.0.101";      //本机IP地址，每次更换网路都需做修改！！！

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

    /**
     * 根据用户ID获取报告单信息
     * @param userID 用户ID
     * @return
     */
    public static List<ReportBean> getReportInfoByUserID(String userID){
        List<ReportBean> list = new ArrayList<>();
        //根据数据库名称，建立连接
        Connection connection = getConn("medical");

        try{
            String sql = "select * from report where PatientID = ?";
            if(connection != null){    //成功与数据库建立连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if(ps != null){
                    ps.setString(1, userID);
                    //执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    if(rs != null){     //查询结果不为空
                        while(rs.next()){
                            ReportBean reportBean = new ReportBean(rs.getString(3), rs.getString(4),
                                    rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8),
                                    rs.getString(9));
                            list.add(reportBean);
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

    /**
     * 匹配用户名和密码
     * @param userName 用户名
     * @param userPass 密码
     * @return 匹配成功则返回UserID，匹配失败则返回null
     */
    public static String loginDataCheck(String userName, String userPass){
        ArrayList<String> list = new ArrayList<>();
        //根据数据库名称，建立连接
        Connection connection = getConn("medical");

        try{
            String sql = "select * from user where UserName = ? and UserPass = ?";
            if(connection != null){    //成功与数据库建立连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if(ps != null){
                    ps.setString(1, userName);
                    ps.setString(2, userPass);
                    //执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    if(rs != null){     //查询结果不为空
                        while(rs.next()){
                            list.add(rs.getString(1));
                        }
                        rs.close();
                        ps.close();
                        connection.close();
                        if(list.size() != 0) {
                            return list.get(0);
                        }else{
                            return null;
                        }
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

    /**
     * 添加用户信息（注册）
     * @param userName 用户名
     * @param userPass 密码
     */
    public static void addRegisterData(String userName, String userPass){
        //根据数据库名称，建立连接
        Connection connection = getConn("medical");

        try{
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String sql = "insert into user values(?,?,?,1)";
            if(connection != null){    //成功与数据库建立连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if(ps != null){
                    ps.setString(1, uuid);
                    ps.setString(2, userName);
                    ps.setString(3, userPass);
                    //执行sql插入语句
                    ps.executeUpdate();
                    ps.close();
                    connection.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加挂号信息
     * @param userID 患者ID
     * @param doctorName 医生姓名
     */
    public static void addCallNumber(String userID, String doctorName){
        if(isCall(userID, doctorName)) {
            return;
        }

        //根据数据库名称，建立连接
        Connection connection = getConn("medical");

        try{
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String sql = "insert into wait values(?,?,?)";
            if(connection != null){    //成功与数据库建立连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if(ps != null){
                    ps.setString(1, uuid);
                    ps.setString(2, userID);
                    ps.setString(3, doctorName);
                    //执行sql插入语句
                    ps.executeUpdate();
                    ps.close();
                    connection.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 判断是否已挂号
     * @param userID 患者ID
     * @param doctorName 医生姓名
     * @return true为已挂号，false为未挂号
     */
    public static boolean isCall(String userID, String doctorName){
        //根据数据库名称，建立连接
        Connection connection = getConn("medical");

        try{
            String sql = "select * from wait where PatientID=? and DoctorName=?";
            if(connection != null){    //成功与数据库建立连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if(ps != null){
                    ps.setString(1, userID);
                    ps.setString(2, doctorName);
                    //执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){     //查询结果不为空
                        rs.close();
                        ps.close();
                        connection.close();
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
