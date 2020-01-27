$(document).ready(function () {
    //获取医生信息
    var doctorName = $.cookie("doctorName");
    $(".welcome span").text(doctorName);
    $("#doctorMain").val(doctorName);
    //获取医生所属科室
    $.ajax({
        type: "POST",
        url: "getOffice.php",
        data: "doctorName="+doctorName,
        success: function (msg) {
            if(msg != ""){
                $("#office").val(transNumToOffice(msg));
            }else{
                alert("加载失败！");
            }
        }
    });
    //获取排队信息
    getTeamMsg(doctorName);
});

//上传报告单
function uploadReport() {
    var patientName = $("#patientName").val();
    var patientID = getPatientID(patientName);
    var patientSex = $("#patientSex").val();
    var patientAge = $("#patientAge").val();
    var office = $("#office").val();
    var doctorMain = $("#doctorMain").val();
    var diagnose = $("#diagnose").val();
    var medicine = $("#medicine").val();
    $.ajax({
        type: "POST",
        url: "uploadReport.php",
        data: "patientID="+patientID+"&patientName="+patientName+"&patientSex="+patientSex+"&patientAge="
            +patientAge+"&office="+transOfficeToNum(office)+"&doctorMain="+doctorMain+"&diagnose="
            +diagnose+"&medicine="+medicine,
        success: function (msg) {
            if(msg == 1){
                alert("上传成功！");
            }else{
                alert("上传失败！");
            }
        }
    });
}

/**
 * 叫号
 */
function callNumber() {
    //获取医生信息
    var doctorName = $.cookie("doctorName");
    var patientName = $(".nextPatient .nextName").text();
    var patientID = $(".currentPatientID").text();
    if(patientName == "无"){
        alert("当前无排队病人！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "deleteTeamTop.php",
        data: "doctorName="+doctorName+"&patientName="+patientID,
        success: function (msg) {
            if(msg == "success"){
                alert("叫号成功！"+patientName+"即将就诊");
            }else{
                alert("叫号失败！");
            }
            getTeamMsg(doctorName);
        }
    });
}

/**
 * 获取挂号排队信息
 */
function getTeamMsg(doctorName) {
    //获取医生信息
    $.ajax({
        type: "POST",
        url: "getTeamInfo.php",
        data: "doctorName="+doctorName,
        success: function (msg) {
            var obj = JSON.parse(msg);
            $(".currentWait b").text(obj.teamNum);    //当前排队人数
            $(".nextName").text(getPatientName(obj.patientID));     //下一个叫号病人ID
            $(".currentPatientID").text(obj.patientID);
        }
    });

}

/**
 * 获取病人姓名
 * @param patientID 病人ID
 */
function getPatientName(patientID){
    var currentPatientName = "";
    $.ajax({
        type: "POST",
        url: "getPatientName.php",
        async: false,
        data: "patientID="+patientID,
        success: function (msg) {
            currentPatientName = msg;
        }
    });
    return currentPatientName;
}

/**
 * 获取病人ID
 * @param patientName 病人姓名
 */
function getPatientID(patientName){
    var patientID = "";
    $.ajax({
        type: "POST",
        url: "getPatientID.php",
        data: "patientName="+patientName,
        async: false,
        success: function (msg) {
            if(msg != ""){
                patientID = msg;
            }
        }
    });
    return patientID;
}

/**
 * 转换科室编号到名称
 * @param msg 科室编号
 */
function transNumToOffice(msg){
    var office = new Array();
    office[0] = "心脏与血管外科";
    office[1] = "肿瘤科";
    office[2] = "男科";
    office[3] = "整形美容科";
    office[4] = "神经外科";
    office[5] = "内分泌与代谢科";
    office[6] = "皮肤性病科";
    office[7] = "呼吸内科";
    office[8] = "内科";
    office[9] = "眼科";
    office[10] = "普外科";
    office[11] = "烧伤科";
    office[12] = "口腔颌面科";
    office[13] = "肾内科";
    office[14] = "甲状腺乳腺外科";
    office[15] = "精神科";
    office[16] = "风湿免疫科";
    office[17] = "泌尿外科";
    office[18] = "耳鼻喉科";
    office[19] = "神经内科";
    office[20] = "消化内科";
    office[21] = "妇科";
    office[22] = "心理科";
    office[23] = "感染科";
    office[24] = "骨科";
    office[25] = "血液病科";
    office[26] = "心血管内科";
    office[27] = "胸外科";
    office[28] = "儿科";
    office[29] = "产科";
    office[30] = "新生儿科";
    office[31] = "急诊科";
    return office[msg];
}

/**
 * 转换科室名称到科室编号
 * @param msg 科室名称
 */
function transOfficeToNum(msg){
    var office = new Array();
    office["心脏与血管外科"] = "0";
    office["肿瘤科"] = "1";
    office["男科"] = "2";
    office["整形美容科"] = "3";
    office["神经外科"] = "4";
    office["内分泌与代谢科"] = "5";
    office["皮肤性病科"] = "6";
    office["呼吸内科"] = "7";
    office["内科"] = "8";
    office["眼科"] = "9";
    office["普外科"] = "10";
    office["烧伤科"] = "11";
    office["口腔颌面科"] = "12";
    office["肾内科"] = "13";
    office["甲状腺乳腺外科"] = "14";
    office["精神科"] = "15";
    office["风湿免疫科"] = "16";
    office["泌尿外科"] = "17";
    office["耳鼻喉科"] = "18";
    office["神经内科"] = "19";
    office["消化内科"] = "20";
    office["妇科"] = "21";
    office["心理科"] = "22";
    office["感染科"] = "23";
    office["骨科"] = "24";
    office["血液病科"] = "25";
    office["心血管内科"] = "26";
    office["胸外科"] = "27";
    office["儿科"] = "28";
    office["产科"] = "29";
    office["新生儿科"] = "30";
    office["急诊科"] = "31";
    return office[msg];
}