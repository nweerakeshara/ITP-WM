/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.itextpdf.text.BaseColor;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.persistence.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
//import javax.swing.text.Document;
import model.Billpayment;
import model.Billtype;
import model.CustomersModel;
import model.Employee;
import model.EmployeeLeaveModel;
import model.EmployeeModel;
import model.EmployeeType;
import model.ItemSupModel;
import model.SMModel;
import model.Suppliers;
import model.SupplyModel;
import model.orderModel;
import net.proteanit.sql.DbUtils;
import service.CusDB;
import service.EmpDB;
import service.IM_Service;
import service.SMServiceImpl;
import service.employeeValidator;
import service.supDB;
import service.supplierValidator;
import util.FBDBConnect;
import util.ShakithDBConnect;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DateFormat;
import javax.mail.internet.ParseException;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.view.*;
import net.sf.jasperreports.engine.*;
import service.ThanujaServiceImpl;
import util.DBConnect;

/**
 *
 * @author Thanuja Chamika
 */
public class NewJFrame extends javax.swing.JFrame {

    String id, path;
    Connection connn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    EmpDB globalEmpDB = new EmpDB();
    supDB globalSupDB = new supDB();
    String p;
    public String vnum;

    /**
     * Creates new form NewJFrame
     */
    IM_Service IM_table = new IM_Service();

    public NewJFrame() {
        initComponents();

        setExtendedState(NewJFrame.MAXIMIZED_BOTH);

        showDate();
        showTime();

        //shakith part
        incrementID();
        loadId();
        incrementID_addVehicle();
        setDate_addVehicle();
        loaddata_deliveryDetails();
        dmDeliveryDetails_table.grabFocus();
        loadData_vehicleDetails();
        dmVehicleDetails_table.grabFocus();

        dmDeliveryDetails_table.getTableHeader().setFont(new Font("SansSerif", 1, 17));
        dmVehicleDetails_table.getTableHeader().setFont(new Font("SansSerif", 1, 17));

        connn = DBConnect.DBConn();
    }

    void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        jLabel15.setText(s.format(d));
    }

    void showTime() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
                jLabel16.setText(s.format(d));
            }

        }).start();
    }

    void viewPanel(String cname) {
        CardLayout cL = (CardLayout) jPanel3.getLayout();
        cL.show(jPanel3, cname);
    }

    void refreshTable(List sList) {
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, billpaymentList, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${type}"));
        columnBinding.setColumnName("Type");
        columnBinding.setColumnClass(model.Billtype.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${eid}"));
        columnBinding.setColumnName("Eid");
        columnBinding.setColumnClass(model.Employee.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${amount}"));
        columnBinding.setColumnName("Amount");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${month}"));
        columnBinding.setColumnName("Month");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${pid}"));
        columnBinding.setColumnName("Pid");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
    }

    void refreshTable2(List sList) {
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, billpaymentList, jTable2);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${type}"));
        columnBinding.setColumnName("Type");
        columnBinding.setColumnClass(model.Billtype.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${eid}"));
        columnBinding.setColumnName("Eid");
        columnBinding.setColumnClass(model.Employee.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${amount}"));
        columnBinding.setColumnName("Amount");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${month}"));
        columnBinding.setColumnName("Month");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${pid}"));
        columnBinding.setColumnName("Pid");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel2 = new javax.swing.JLabel();
        ITPPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("ITPPU").createEntityManager();
        billpaymentQuery = java.beans.Beans.isDesignTime() ? null : ITPPUEntityManager.createQuery("SELECT b FROM Billpayment b");
        billpaymentList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : billpaymentQuery.getResultList();
        billtypeQuery = java.beans.Beans.isDesignTime() ? null : ITPPUEntityManager.createQuery("SELECT b FROM Billtype b");
        billtypeList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : billtypeQuery.getResultList();
        employeeQuery = java.beans.Beans.isDesignTime() ? null : ITPPUEntityManager.createQuery("SELECT e FROM Employee e");
        employeeList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : employeeQuery.getResultList();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        SaMjPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnViewReports = new javax.swing.JButton();
        btnHandleReturns = new javax.swing.JButton();
        btnRegisterCustomers = new javax.swing.JButton();
        btnGenerateBills = new javax.swing.JButton();
        CMjPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        AddCustomers = new javax.swing.JButton();
        ManageCustomers = new javax.swing.JButton();
        Reports = new javax.swing.JButton();
        FMjPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        IM_report = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        IM_search = new javax.swing.JButton();
        IM_update_delete = new javax.swing.JButton();
        IM_add = new javax.swing.JButton();
        IM_search1 = new javax.swing.JButton();
        PMjPanel7 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        PMjLabel6 = new javax.swing.JLabel();
        pmInsertBillPayments = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton14 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        DMjPanel9 = new javax.swing.JPanel();
        dmjButton5 = new javax.swing.JButton();
        dmjButton4 = new javax.swing.JButton();
        dmjButton3 = new javax.swing.JButton();
        dmjButton2 = new javax.swing.JButton();
        dmjButton1 = new javax.swing.JButton();
        dmjLabel1 = new javax.swing.JLabel();
        EMjPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        attendanceEmpNavBtn = new javax.swing.JButton();
        leaveEmpNavBtn = new javax.swing.JButton();
        regEmpNavBtn = new javax.swing.JButton();
        addEmpCatNavBtn = new javax.swing.JButton();
        empLeaveMgmtNavBtn = new javax.swing.JButton();
        empReports = new javax.swing.JButton();
        attendanceEmpCard = new javax.swing.JPanel();
        empDateLabel = new javax.swing.JLabel();
        empSignOutBtn = new javax.swing.JButton();
        empSignInBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        empAttendanceTable = new javax.swing.JTable();
        addCatergoryPanel = new javax.swing.JPanel();
        empAddTypeLabel = new javax.swing.JLabel();
        empTypeIdBox = new javax.swing.JTextField();
        empSalaryLabel = new javax.swing.JLabel();
        empSalaryBox = new javax.swing.JTextField();
        empLeaveLabel = new javax.swing.JLabel();
        empLeaveBox = new javax.swing.JTextField();
        updateEmpTypeBtn = new javax.swing.JButton();
        deleteEmpTypeBtn = new javax.swing.JButton();
        addEmpTypeBtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        empCatergoryTable = new javax.swing.JTable();
        empTypeIdLabel = new javax.swing.JLabel();
        empTypeBox1 = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        empLeaveMgmtPanel = new javax.swing.JPanel();
        empReasonLabel_mgmt = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        empReasonText_mgmt = new javax.swing.JTextArea();
        empApproveRequestBtn_mgmt = new javax.swing.JButton();
        empstartDateBox_mgmt = new javax.swing.JTextField();
        empstartDateLabel_mgmt = new javax.swing.JLabel();
        empEndDateBox_mgmt = new javax.swing.JTextField();
        empEndDateLabel_mgmt = new javax.swing.JLabel();
        empDaysLabel_mgmt = new javax.swing.JLabel();
        empDaysBox_mgmt = new javax.swing.JTextField();
        empID_mgmt1 = new javax.swing.JTextField();
        empstartDateLabel_mgmt1 = new javax.swing.JLabel();
        empEndDateBox_mgmt1 = new javax.swing.JTextField();
        empEndDateLabel_mgmt1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        empLeaveRequestTable = new javax.swing.JTable();
        empRejectRequestBtn_mgmt = new javax.swing.JButton();
        empLeaveReqIDLabel_mgmt = new javax.swing.JLabel();
        empLeaveReqID_mgmt = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        smGenerateBills = new javax.swing.JPanel();
        smjLabel4 = new javax.swing.JLabel();
        custp = new javax.swing.JTextField();
        smjLabel5 = new javax.swing.JLabel();
        btnNxtGenerateBill = new javax.swing.JButton();
        custpaymentmethod = new javax.swing.JComboBox<>();
        smCustIdStatus = new javax.swing.JLabel();
        smjLabel6 = new javax.swing.JLabel();
        smdisref = new javax.swing.JTextField();
        smjLabel7 = new javax.swing.JLabel();
        smitemid = new javax.swing.JTextField();
        smjLabel8 = new javax.swing.JLabel();
        smitemqty = new javax.swing.JTextField();
        smbtnadditem = new javax.swing.JButton();
        smbtndeleteitem = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        smbilltable = new javax.swing.JTable();
        smqtystatus = new javax.swing.JLabel();
        smdisrefstatus = new javax.swing.JLabel();
        smreset = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JSeparator();
        smReturnHandle = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        smviewreturnstable = new javax.swing.JTable();
        smenterbillid = new javax.swing.JLabel();
        smshowitemid = new javax.swing.JLabel();
        smshowitemname = new javax.swing.JLabel();
        smtxtfieldenterbillid = new javax.swing.JTextField();
        smtxtitemid = new javax.swing.JTextField();
        smtxtitemqty = new javax.swing.JTextField();
        smbtnsearchbillid = new javax.swing.JButton();
        smbtndeleteitemreturns = new javax.swing.JButton();
        smsalesidreturns = new javax.swing.JLabel();
        smsalesidreturnsbox = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        smGenerateBills2 = new javax.swing.JPanel();
        smjLabel9 = new javax.swing.JLabel();
        smtotbillamt = new javax.swing.JTextField();
        smjLabel10 = new javax.swing.JLabel();
        smcashamtpaid = new javax.swing.JTextField();
        smjLabelch = new javax.swing.JLabel();
        smchangedis = new javax.swing.JTextField();
        pmSearchBillPayments = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        cmAddCus = new javax.swing.JPanel();
        head_add_cus = new javax.swing.JLabel();
        name_cus = new javax.swing.JLabel();
        phone_cus = new javax.swing.JLabel();
        address_cus_ad = new javax.swing.JLabel();
        email_cus_ad = new javax.swing.JLabel();
        reset_cus_add = new javax.swing.JButton();
        submit_cuss_add = new javax.swing.JButton();
        cusEmailAdd = new javax.swing.JTextField();
        cusNameAdd = new javax.swing.JTextField();
        cusPhoneAdd = new javax.swing.JTextField();
        cusAddAdd = new javax.swing.JTextField();
        message = new javax.swing.JLabel();
        ID_Val = new javax.swing.JLabel();
        phone_val = new javax.swing.JLabel();
        name_val = new javax.swing.JLabel();
        address_val = new javax.swing.JLabel();
        email_val = new javax.swing.JLabel();
        cmUDCus = new javax.swing.JPanel();
        phoneBoxCus = new javax.swing.JTextField();
        emailBoxCus = new javax.swing.JTextField();
        ID_cus = new javax.swing.JLabel();
        manage_cus = new javax.swing.JLabel();
        cancel_cus = new javax.swing.JButton();
        address_cus = new javax.swing.JLabel();
        name_cus_u = new javax.swing.JTextField();
        phone_cus_u = new javax.swing.JLabel();
        email_cus_U = new javax.swing.JLabel();
        adress_cus_u = new javax.swing.JTextField();
        name_cus_t = new javax.swing.JLabel();
        delete_cus_U = new javax.swing.JButton();
        update_cus_u = new javax.swing.JButton();
        cus_view_scroll = new javax.swing.JScrollPane();
        cus_view_table = new javax.swing.JTable();
        labCusID = new javax.swing.JLabel();
        name_val_U = new javax.swing.JLabel();
        phone_val_U1 = new javax.swing.JLabel();
        address_val_U2 = new javax.swing.JLabel();
        email_val_U3 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        Cus_search = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        IMsearch = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        IM_Stable = new javax.swing.JTable();
        IM_serch_by = new javax.swing.JLabel();
        IM_key = new javax.swing.JTextField();
        IM_Sby = new javax.swing.JComboBox();
        IM_search_btn = new javax.swing.JButton();
        IM_SEARCH_txt = new javax.swing.JLabel();
        IMupdate = new javax.swing.JPanel();
        IM_name_lbl = new javax.swing.JLabel();
        IM_type_lbl = new javax.swing.JLabel();
        IM_qty_lbl = new javax.swing.JLabel();
        IM_bprice_lbl = new javax.swing.JLabel();
        IM_sprice_lbl = new javax.swing.JLabel();
        IM_ID_lbl = new javax.swing.JLabel();
        I_ID = new javax.swing.JLabel();
        I_Uname = new javax.swing.JTextField();
        I_Uqty = new javax.swing.JTextField();
        I_Utype = new javax.swing.JTextField();
        I_USprice = new javax.swing.JTextField();
        I_UBprice = new javax.swing.JTextField();
        IM_update_btn = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        IM_Utable = new javax.swing.JTable();
        IM_delete_btn = new javax.swing.JButton();
        IM_U_qty_error_lbl = new javax.swing.JLabel();
        IM_U_Bprice_error_lbl = new javax.swing.JLabel();
        IM_U_Sprice_error_lbl = new javax.swing.JLabel();
        IM_U_update = new javax.swing.JLabel();
        IM_U_name_error_lbl = new javax.swing.JLabel();
        IM_U_brand_error_lbl = new javax.swing.JLabel();
        IM_U_delete = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        IM_UPDATE_txt = new javax.swing.JLabel();
        IMadd = new javax.swing.JPanel();
        IM_name = new javax.swing.JLabel();
        IM_type = new javax.swing.JLabel();
        IM_qty = new javax.swing.JLabel();
        IM_bprice = new javax.swing.JLabel();
        IM_sprice = new javax.swing.JLabel();
        I_name = new javax.swing.JTextField();
        I_qty = new javax.swing.JTextField();
        I_type = new javax.swing.JTextField();
        I_Sprice = new javax.swing.JTextField();
        I_Bprice = new javax.swing.JTextField();
        IM_add_btn = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        IM_Atable = new javax.swing.JTable();
        IM_A_qty_error_lbl = new javax.swing.JLabel();
        IM_A_Bprice_error_lbl = new javax.swing.JLabel();
        IM_A_Sprice_error_lbl = new javax.swing.JLabel();
        IM_A_success = new javax.swing.JLabel();
        IM_A_name_error_lbl = new javax.swing.JLabel();
        IM_A_brand_error_lbl = new javax.swing.JLabel();
        IM_ADD_txt = new javax.swing.JLabel();
        sumPanel16 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        SuMOrder = new javax.swing.JButton();
        SuMPlace = new javax.swing.JButton();
        SuMAdd = new javax.swing.JButton();
        dmAddDelivery_Panel_AddDelivery = new javax.swing.JPanel();
        dmAddDelivery_Label_Title = new javax.swing.JLabel();
        dmAddDelivery_Panel_DeliveryInfo = new javax.swing.JPanel();
        dmAddDelivery_Label_DeliveryID = new javax.swing.JLabel();
        dmAddDelivery_billID = new javax.swing.JComboBox<>();
        dmAddDelivery_Label_Billid = new javax.swing.JLabel();
        dmAddDelivery_Label_custName = new javax.swing.JLabel();
        dmAddDelivery_custName = new javax.swing.JTextField();
        dmAddDelivery_Label_date = new javax.swing.JLabel();
        dmAddDelivery_Label_time = new javax.swing.JLabel();
        dmAddDelivery_time = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        dmAddDelivery_DeliveryID = new javax.swing.JTextField();
        dmAddDelivery_date = new com.toedter.calendar.JDateChooser();
        dmAddDelivery_Panel_LocationInfo = new javax.swing.JPanel();
        dmAddDelivery_Label_address = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        dmAddDelivery_address = new javax.swing.JTextArea();
        dmAddDelivery_Label_town = new javax.swing.JLabel();
        dmAddDelivery_town = new javax.swing.JTextField();
        dmAddDelivery_Panel_VehicleInfp = new javax.swing.JPanel();
        dmAddDelivery_Label_vehicle = new javax.swing.JLabel();
        dmAddDelivery_VehicleId = new javax.swing.JComboBox<>();
        dmAddDelivery_Label_type = new javax.swing.JLabel();
        dmAddDelivery_Label_driver = new javax.swing.JLabel();
        dmAddDelivery_Driver = new javax.swing.JComboBox<>();
        dmAddDelivery_LabelName = new javax.swing.JLabel();
        dmAddDelivery_Name = new javax.swing.JTextField();
        dmAddDelivery_type = new javax.swing.JTextField();
        dmAddDelivery_btn_AddDelivery = new javax.swing.JButton();
        dmAddDelivery_btnReset = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        dmAddVehicle_Panel_AddVehicle = new javax.swing.JPanel();
        dmAddDelivery_Label_Title1 = new javax.swing.JLabel();
        dmAddVehicle_Panel_vehicleInfo = new javax.swing.JPanel();
        dmAddVehicle_vehicleId = new javax.swing.JTextField();
        dmAddVehicle_Label_Resgister = new javax.swing.JLabel();
        dmAddVehicle_registerNum = new javax.swing.JTextField();
        dmAddVehicle_Label_make = new javax.swing.JLabel();
        dmAddVehicle_make = new javax.swing.JComboBox<>();
        dmAddVehicle_Label_category = new javax.swing.JLabel();
        dmAddVehicle_category = new javax.swing.JComboBox<>();
        dmAddVehicle_Label_fuelType = new javax.swing.JLabel();
        dmAddVehicle_fuelType = new javax.swing.JComboBox<>();
        dmAddVehicle_Label_desc = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        dmAddVehicle_desc = new javax.swing.JTextArea();
        dmAddVehicle_Label_date = new javax.swing.JLabel();
        dmAddVehicle_Label_VehicleID = new javax.swing.JLabel();
        dmAddVehicle_date = new javax.swing.JTextField();
        dmAddVehicle_Label_capacity = new javax.swing.JLabel();
        dmAddVehicle_capacity = new javax.swing.JTextField();
        dmAddVehicle_Label_kg = new javax.swing.JLabel();
        dmAddVehicle_Panel_vehicleImg = new javax.swing.JPanel();
        dmAddVehicle_Label_img = new javax.swing.JLabel();
        dmAddVehicle_btnChooseImg = new javax.swing.JButton();
        dmAddVehicle_btnAddVehicle = new javax.swing.JButton();
        dmAddVehicle_btnReset = new javax.swing.JButton();
        dmDeliveryDetails_Panel_DeliveryDetails = new javax.swing.JPanel();
        dmDeliveryDetails_Label_Title = new javax.swing.JLabel();
        dmDeliveryDetails_Search = new javax.swing.JTextField();
        dmDeliveryDetails_Searchbtn = new javax.swing.JButton();
        jScrollPane17 = new javax.swing.JScrollPane();
        dmDeliveryDetails_table = new javax.swing.JTable();
        dmDeliveryDetailsDisc_Panel_DeliveryDetals = new javax.swing.JPanel();
        dmDeliveryDetailsDisc_Label_title = new javax.swing.JLabel();
        dmDeliveryDetailsDisc_Panel_DeliveryInfo = new javax.swing.JPanel();
        dmDeliveryDetailsDisc_Label_deliveryID = new javax.swing.JLabel();
        dmDeliveryDetailsDisc_Label_billID = new javax.swing.JLabel();
        dmDeliveryDetailsDisc_Label_custName = new javax.swing.JLabel();
        dmDeliveryDetailsDisc_custName = new javax.swing.JTextField();
        dmDeliveryDetailsDisc_Label_date = new javax.swing.JLabel();
        dmDeliveryDetailsDisc_date = new javax.swing.JTextField();
        dmDeliveryDetailsDisc_Label_time = new javax.swing.JLabel();
        dmDeliveryDetailsDisc_time = new javax.swing.JTextField();
        dmDeliveryDetailsDisc_billID = new javax.swing.JTextField();
        dmDeliveryDetailsDisc_DeliveryID = new javax.swing.JTextField();
        dmDeliveryDetailsDisc_Panel_location = new javax.swing.JPanel();
        dmDeliveryDetailsDisc_Label_address = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        dmDeliveryDetails_address = new javax.swing.JTextArea();
        dmDeliveryDetailsDisc_Label_town = new javax.swing.JLabel();
        dmDeliveryDetailsDisc_town = new javax.swing.JTextField();
        dmDeliveryDetailsDisc_Panel_vehicleINFO = new javax.swing.JPanel();
        dmDeliveryDetailsDisc_Label_vehicleID = new javax.swing.JLabel();
        dmDeliveryDetailsDisc_Label_type = new javax.swing.JLabel();
        dmDeliveryDetailsDisc_type = new javax.swing.JTextField();
        dmDeliveryDetails_vehicleID = new javax.swing.JTextField();
        dmDeliveryDetailsDisc_btn_delete = new javax.swing.JButton();
        dmDeliveryDetailsDisc_btn_Update = new javax.swing.JButton();
        dmupdateDelivery_Panel_UpdateDelivery = new javax.swing.JPanel();
        dmupdateDelivery_Label_title = new javax.swing.JLabel();
        dmupdateDelivery_Panel_DeliveryInfo = new javax.swing.JPanel();
        dmupdateDelivery_Label_deliveryID = new javax.swing.JLabel();
        dmupdateDelivery_Label_BillID = new javax.swing.JLabel();
        dmupdateDelivery_Label_custName = new javax.swing.JLabel();
        dmupdateDelivery_custName = new javax.swing.JTextField();
        dmupdateDelivery_Label_date = new javax.swing.JLabel();
        dmupdateDelivery_Date = new javax.swing.JTextField();
        dmupdateDelivery_Label_time = new javax.swing.JLabel();
        dmupdateDelivery_time = new javax.swing.JTextField();
        dmupdateDelivery_billID = new javax.swing.JTextField();
        dmupdateDelivery_DeliveryID = new javax.swing.JTextField();
        dmupdateDelivery_Label_am = new javax.swing.JComboBox<>();
        dmupdateDelivery_Panel_Location = new javax.swing.JPanel();
        dmupdateDelivery_Label_address = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        dmupdateDelivery_address = new javax.swing.JTextArea();
        dmupdateDelivery_Label_town = new javax.swing.JLabel();
        dmupdateDelivery_town = new javax.swing.JTextField();
        dmupdateDelivery_Panel_Vehicle = new javax.swing.JPanel();
        dmupdateDelivery_Label_VehicleID = new javax.swing.JLabel();
        dmupdateDelivery_vehicleID = new javax.swing.JComboBox<>();
        dmupdateDelivery_Label_type = new javax.swing.JLabel();
        dmupdateDelivery_type = new javax.swing.JTextField();
        dmupdateDelivery_btn_Update = new javax.swing.JButton();
        dmVehicleDetails_Panel_main = new javax.swing.JPanel();
        dmVehicleDetails_Label_title = new javax.swing.JLabel();
        dmVehicleDetails_search = new javax.swing.JTextField();
        dmVehicleDetails_btn = new javax.swing.JButton();
        dmVehicleDetails_scroll = new javax.swing.JScrollPane();
        dmVehicleDetails_table = new javax.swing.JTable();
        dmVehicleDetailsDisc_Panel_Main = new javax.swing.JPanel();
        dmVehicleDetailsDisc_Label_title = new javax.swing.JLabel();
        dmVehicleDetailsDisc_Label_id = new javax.swing.JLabel();
        dmVehicleDetailsDisc_id = new javax.swing.JTextField();
        dmVehicleDetailsDisc_Label_num = new javax.swing.JLabel();
        dmVehicleDetailsDisc_register = new javax.swing.JTextField();
        dmVehicleDetailsDisc_Label_cat = new javax.swing.JLabel();
        dmVehicleDetailsDisc_Label_make = new javax.swing.JLabel();
        dmVehicleDetailsDisc_Label_fuel = new javax.swing.JLabel();
        dmVehicleDetailsDisc_Label_capa = new javax.swing.JLabel();
        dmVehicleDetailsDisc_Label_desc = new javax.swing.JLabel();
        dmVehicleDetailsDisc_make = new javax.swing.JTextField();
        dmVehicleDetailsDisc_cate = new javax.swing.JTextField();
        dmVehicleDetailsDisc_fuel = new javax.swing.JTextField();
        dmVehicleDetailsDisc_capa = new javax.swing.JTextField();
        jScrollPane20 = new javax.swing.JScrollPane();
        dmVehicleDetailsDisc_desc = new javax.swing.JTextArea();
        dmVehicleDetailsDisc_Labeldate = new javax.swing.JLabel();
        dmVehicleDetailsDisc_date = new javax.swing.JTextField();
        dmVehicleDetailsDisc_Label_kg = new javax.swing.JLabel();
        dmVehicleDetailsDisc_btnUpdate = new javax.swing.JButton();
        dmVehicleDetailsDisc_btnDelete = new javax.swing.JButton();
        dmVehicleDetailsDisc_Label_img = new javax.swing.JLabel();
        dmUpdateVehicle_Panel_main = new javax.swing.JPanel();
        dmUpdateVehicle_Label_title = new javax.swing.JLabel();
        dmUpdateVehicle_Panel_info = new javax.swing.JPanel();
        dmUpdateVehicle_vehicleId = new javax.swing.JTextField();
        dmUpdateVehicle_Label_num = new javax.swing.JLabel();
        dmUpdateVehicle_registerNum = new javax.swing.JTextField();
        dmUpdateVehicle_Label_make = new javax.swing.JLabel();
        dmUpdateVehicle_make = new javax.swing.JComboBox<>();
        dmUpdateVehicle_Label_cate = new javax.swing.JLabel();
        dmUpdateVehicle_category = new javax.swing.JComboBox<>();
        dmUpdateVehicle_Label_fuel = new javax.swing.JLabel();
        dmUpdateVehicle_fuelType = new javax.swing.JComboBox<>();
        dmUpdateVehicle_Label_desc = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        dmUpdateVehicle_desc = new javax.swing.JTextArea();
        datedmUpdateVehicle_Label_date = new javax.swing.JLabel();
        dmUpdateVehicle_Label_id = new javax.swing.JLabel();
        dmUpdateVehicle_date = new javax.swing.JTextField();
        dmUpdateVehicle_Label_capa = new javax.swing.JLabel();
        dmUpdateVehicle_capacity = new javax.swing.JTextField();
        dmUpdateVehicle_kg = new javax.swing.JLabel();
        dmUpdateVehicle_Panel_img = new javax.swing.JPanel();
        dmUpdateVehicle_Label_img = new javax.swing.JLabel();
        dmUpdateVehicle_btnChoose_ = new javax.swing.JButton();
        dmUpdateVehicle_btnUpdate = new javax.swing.JButton();
        supOrderDetails = new javax.swing.JPanel();
        Ordrtble = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        amounttxt = new javax.swing.JTextField();
        orderIDtxt = new javax.swing.JTextField();
        Deletebtnorder = new javax.swing.JButton();
        suppIdlabel = new javax.swing.JLabel();
        qtyLabel = new javax.swing.JLabel();
        quantitytxt = new javax.swing.JTextField();
        itemIdLabel = new javax.swing.JLabel();
        supIDcombo = new javax.swing.JComboBox();
        Itemcombo = new javax.swing.JComboBox();
        OrderIdlabel = new javax.swing.JLabel();
        amountLabel = new javax.swing.JLabel();
        placeOrder = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JSeparator();
        orderjLabel9 = new javax.swing.JLabel();
        orderjComboBox3 = new javax.swing.JComboBox();
        OrderjTextField5 = new javax.swing.JTextField();
        supAddDetails = new javax.swing.JPanel();
        supPno = new javax.swing.JTextField();
        supEmail = new javax.swing.JTextField();
        supName = new javax.swing.JTextField();
        supID = new javax.swing.JTextField();
        supUpdatebtn = new javax.swing.JButton();
        supAddbtn = new javax.swing.JButton();
        supNolabel = new javax.swing.JLabel();
        supEmaillabel = new javax.swing.JLabel();
        supNamelabel = new javax.swing.JLabel();
        supIdlabel = new javax.swing.JLabel();
        supAddresslabel = new javax.swing.JLabel();
        supAddress = new javax.swing.JTextField();
        supTble = new javax.swing.JScrollPane();
        supTable = new javax.swing.JTable();
        supDeletebtn = new javax.swing.JButton();
        jSeparator13 = new javax.swing.JSeparator();
        supjLabel9 = new javax.swing.JLabel();
        supjComboBox3 = new javax.swing.JComboBox();
        supjTextField5 = new javax.swing.JTextField();
        supDemo = new javax.swing.JButton();
        empLeaveCard = new javax.swing.JPanel();
        empRemLeaveLabel = new javax.swing.JLabel();
        empRemLeavBox = new javax.swing.JTextField();
        empstartDateLabel = new javax.swing.JLabel();
        empEndDateLabel = new javax.swing.JLabel();
        empDaysLabel = new javax.swing.JLabel();
        empDaysBox = new javax.swing.JTextField();
        empReasonLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        empReasonText = new javax.swing.JTextArea();
        empLeaveRequestBtn = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        empRequestTable = new javax.swing.JTable();
        empLeaveStatusLabel = new javax.swing.JLabel();
        empLeaveStatusBox = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        empLeaveStartDate = new com.toedter.calendar.JDateChooser();
        empLeaveEndDate = new com.toedter.calendar.JDateChooser();
        addEmpCard1 = new javax.swing.JPanel();
        addEmpTitleTxt = new javax.swing.JLabel();
        empNameLabel = new javax.swing.JLabel();
        empNameBox = new javax.swing.JTextField();
        empNICBox = new javax.swing.JTextField();
        empNicLabel = new javax.swing.JLabel();
        empAddressLabel = new javax.swing.JLabel();
        empAddressBox = new javax.swing.JTextField();
        empPhoneLabel = new javax.swing.JLabel();
        empPhoneBox = new javax.swing.JTextField();
        empEmailLabel = new javax.swing.JLabel();
        empEmailBox = new javax.swing.JTextField();
        empTypeLabel = new javax.swing.JLabel();
        emptypeComboBox = new javax.swing.JComboBox<>();
        deleteEmpBtn = new javax.swing.JButton();
        registerEmpBtn1 = new javax.swing.JButton();
        updateEmpBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        empTable = new javax.swing.JTable();
        emIDLabel = new javax.swing.JLabel();
        empIdBox = new javax.swing.JTextField();
        EmpSearchBarField = new javax.swing.JTextField();
        smViewReports = new javax.swing.JPanel();
        smreportbtnsales = new javax.swing.JButton();
        smreportbtnbills = new javax.swing.JButton();
        smreportbtnsalerec = new javax.swing.JButton();
        smRegisterCustomers = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        smtablebills = new javax.swing.JTable();
        jScrollPane23 = new javax.swing.JScrollPane();
        smtablesales = new javax.swing.JTable();
        dm_report = new javax.swing.JPanel();
        dm_vehicleReport = new javax.swing.JButton();
        dm_deliveryReport = new javax.swing.JButton();
        dm_reportTitle = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));

        jPanel1.setBackground(new java.awt.Color(6, 10, 34));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Historic", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Dashboard");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sup m.png"))); // NOI18N
        jButton1.setText("Suppliers management");
        jButton1.setPreferredSize(new java.awt.Dimension(263, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 260, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/empmen.png"))); // NOI18N
        jButton2.setText("Employee management");
        jButton2.setPreferredSize(new java.awt.Dimension(263, 40));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 260, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/inve m.png"))); // NOI18N
        jButton3.setText("Inventory management");
        jButton3.setPreferredSize(new java.awt.Dimension(263, 40));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 260, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/payment main.png"))); // NOI18N
        jButton4.setText("Payments management");
        jButton4.setPreferredSize(new java.awt.Dimension(263, 40));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, -1, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sales.png"))); // NOI18N
        jButton5.setText("Sales management");
        jButton5.setPreferredSize(new java.awt.Dimension(263, 40));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, -1, -1));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/del m.png"))); // NOI18N
        jButton6.setText("Delivery management");
        jButton6.setPreferredSize(new java.awt.Dimension(263, 40));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, -1, -1));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cus m.png"))); // NOI18N
        jButton7.setText("Customer management");
        jButton7.setPreferredSize(new java.awt.Dimension(263, 40));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, -1, -1));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fbm.png"))); // NOI18N
        jButton8.setText("Feedback management");
        jButton8.setPreferredSize(new java.awt.Dimension(263, 40));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 680, -1, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 102, 230, 20));

        jPanel2.setBackground(new java.awt.Color(23, 37, 47));
        jPanel2.setPreferredSize(new java.awt.Dimension(691, 126));

        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Wijesinghe Motors");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("jLabel15");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("jLabel16");

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cogwheel logo.png"))); // NOI18N
        jLabel20.setText("jLabel20");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(574, 574, 574)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(547, 547, 547)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(370, 370, 370))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setMaximumSize(new java.awt.Dimension(0, 0));
        jPanel3.setLayout(new java.awt.CardLayout());

        SaMjPanel8.setBackground(new java.awt.Color(153, 153, 153));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel8.setText("Sales management");

        btnViewReports.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view reports for sales and inventory  and other all managements.png"))); // NOI18N
        btnViewReports.setText("View Reports");
        btnViewReports.setPreferredSize(new java.awt.Dimension(232, 232));
        btnViewReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewReportsActionPerformed(evt);
            }
        });

        btnHandleReturns.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/handle ret.png"))); // NOI18N
        btnHandleReturns.setText("Handle Returns");
        btnHandleReturns.setPreferredSize(new java.awt.Dimension(232, 232));
        btnHandleReturns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHandleReturnsActionPerformed(evt);
            }
        });

        btnRegisterCustomers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view sales details.png"))); // NOI18N
        btnRegisterCustomers.setText("Register Customers");
        btnRegisterCustomers.setPreferredSize(new java.awt.Dimension(232, 232));
        btnRegisterCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterCustomersActionPerformed(evt);
            }
        });

        btnGenerateBills.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/generate bills.png"))); // NOI18N
        btnGenerateBills.setText("Generate Bills");
        btnGenerateBills.setPreferredSize(new java.awt.Dimension(232, 232));
        btnGenerateBills.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateBillsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SaMjPanel8Layout = new javax.swing.GroupLayout(SaMjPanel8);
        SaMjPanel8.setLayout(SaMjPanel8Layout);
        SaMjPanel8Layout.setHorizontalGroup(
            SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SaMjPanel8Layout.createSequentialGroup()
                .addGroup(SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SaMjPanel8Layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(btnRegisterCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127)
                        .addComponent(btnGenerateBills, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(btnHandleReturns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(btnViewReports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SaMjPanel8Layout.createSequentialGroup()
                        .addGap(605, 605, 605)
                        .addComponent(jLabel8)))
                .addContainerGap(605, Short.MAX_VALUE))
        );
        SaMjPanel8Layout.setVerticalGroup(
            SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SaMjPanel8Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170)
                .addGroup(SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRegisterCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGenerateBills, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnViewReports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHandleReturns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(SaMjPanel8, "card6");

        CMjPanel10.setBackground(new java.awt.Color(153, 153, 153));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel10.setText("Customer management");

        AddCustomers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add cus for customer management.png"))); // NOI18N
        AddCustomers.setText("Add Customers");
        AddCustomers.setPreferredSize(new java.awt.Dimension(232, 232));
        AddCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCustomersActionPerformed(evt);
            }
        });

        ManageCustomers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/manage customers.png"))); // NOI18N
        ManageCustomers.setText("Manage Customers");
        ManageCustomers.setPreferredSize(new java.awt.Dimension(232, 232));
        ManageCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageCustomersActionPerformed(evt);
            }
        });

        Reports.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view reports for sales and inventory  and other all managements.png"))); // NOI18N
        Reports.setText("Reports");
        Reports.setPreferredSize(new java.awt.Dimension(232, 232));
        Reports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CMjPanel10Layout = new javax.swing.GroupLayout(CMjPanel10);
        CMjPanel10.setLayout(CMjPanel10Layout);
        CMjPanel10Layout.setHorizontalGroup(
            CMjPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CMjPanel10Layout.createSequentialGroup()
                .addGroup(CMjPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CMjPanel10Layout.createSequentialGroup()
                        .addGap(534, 534, 534)
                        .addComponent(jLabel10))
                    .addGroup(CMjPanel10Layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(AddCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156)
                        .addComponent(ManageCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(166, 166, 166)
                        .addComponent(Reports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(837, Short.MAX_VALUE))
        );
        CMjPanel10Layout.setVerticalGroup(
            CMjPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CMjPanel10Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170)
                .addGroup(CMjPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ManageCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Reports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(6847, Short.MAX_VALUE))
        );

        jPanel3.add(CMjPanel10, "card8");

        FMjPanel11.setBackground(new java.awt.Color(153, 153, 153));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel11.setText("Feedback management");

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/manage feedbacks.png"))); // NOI18N
        jButton16.setText("Manage Feedback");
        jButton16.setPreferredSize(new java.awt.Dimension(232, 232));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FMjPanel11Layout = new javax.swing.GroupLayout(FMjPanel11);
        FMjPanel11.setLayout(FMjPanel11Layout);
        FMjPanel11Layout.setHorizontalGroup(
            FMjPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FMjPanel11Layout.createSequentialGroup()
                .addGroup(FMjPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FMjPanel11Layout.createSequentialGroup()
                        .addGap(550, 550, 550)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FMjPanel11Layout.createSequentialGroup()
                        .addGap(669, 669, 669)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1023, Short.MAX_VALUE))
        );
        FMjPanel11Layout.setVerticalGroup(
            FMjPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FMjPanel11Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6912, Short.MAX_VALUE))
        );

        jPanel3.add(FMjPanel11, "card9");

        IM_report.setBackground(new java.awt.Color(153, 153, 153));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel5.setText("Inventory management");

        IM_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search items for inventory.png"))); // NOI18N
        IM_search.setText("search");
        IM_search.setPreferredSize(new java.awt.Dimension(232, 232));
        IM_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_searchActionPerformed(evt);
            }
        });

        IM_update_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update delete items of inventory.png"))); // NOI18N
        IM_update_delete.setText("update / delete");
        IM_update_delete.setPreferredSize(new java.awt.Dimension(232, 232));
        IM_update_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_update_deleteActionPerformed(evt);
            }
        });

        IM_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add items for inventory.png"))); // NOI18N
        IM_add.setText("add item");
        IM_add.setPreferredSize(new java.awt.Dimension(232, 232));
        IM_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_addActionPerformed(evt);
            }
        });

        IM_search1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view reports for sales and inventory  and other all managements.png"))); // NOI18N
        IM_search1.setText("report");
        IM_search1.setPreferredSize(new java.awt.Dimension(232, 232));
        IM_search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_search1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout IM_reportLayout = new javax.swing.GroupLayout(IM_report);
        IM_report.setLayout(IM_reportLayout);
        IM_reportLayout.setHorizontalGroup(
            IM_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IM_reportLayout.createSequentialGroup()
                .addGroup(IM_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IM_reportLayout.createSequentialGroup()
                        .addGap(546, 546, 546)
                        .addComponent(jLabel5))
                    .addGroup(IM_reportLayout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(IM_add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(IM_update_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(IM_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(IM_search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(564, Short.MAX_VALUE))
        );
        IM_reportLayout.setVerticalGroup(
            IM_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IM_reportLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel5)
                .addGap(155, 155, 155)
                .addGroup(IM_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IM_update_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IM_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IM_search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IM_add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(IM_report, "card3");

        PMjPanel7.setBackground(new java.awt.Color(153, 153, 153));

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/insert bill payments.png"))); // NOI18N
        jButton10.setText("Insert bill payments");
        jButton10.setPreferredSize(new java.awt.Dimension(232, 232));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search bill payments.png"))); // NOI18N
        jButton11.setText("Search bill payments");
        jButton11.setPreferredSize(new java.awt.Dimension(232, 232));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view reports for sales and inventory  and other all managements.png"))); // NOI18N
        jButton12.setText("Generate reports");
        jButton12.setPreferredSize(new java.awt.Dimension(232, 232));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        PMjLabel6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        PMjLabel6.setText("Payments management");

        javax.swing.GroupLayout PMjPanel7Layout = new javax.swing.GroupLayout(PMjPanel7);
        PMjPanel7.setLayout(PMjPanel7Layout);
        PMjPanel7Layout.setHorizontalGroup(
            PMjPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PMjPanel7Layout.createSequentialGroup()
                .addGroup(PMjPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PMjPanel7Layout.createSequentialGroup()
                        .addGap(555, 555, 555)
                        .addComponent(PMjLabel6))
                    .addGroup(PMjPanel7Layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(932, Short.MAX_VALUE))
        );

        PMjPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton10, jButton11, jButton12});

        PMjPanel7Layout.setVerticalGroup(
            PMjPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PMjPanel7Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(PMjLabel6)
                .addGap(170, 170, 170)
                .addGroup(PMjPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(6886, Short.MAX_VALUE))
        );

        jPanel3.add(PMjPanel7, "card5");

        pmInsertBillPayments.setBackground(new java.awt.Color(153, 153, 153));

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, billpaymentList, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${pid}"));
        columnBinding.setColumnName("Pid");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${type}"));
        columnBinding.setColumnName("Type");
        columnBinding.setColumnClass(model.Billtype.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${month}"));
        columnBinding.setColumnName("Month");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${amount}"));
        columnBinding.setColumnName("Amount");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${eid}"));
        columnBinding.setColumnName("Eid");
        columnBinding.setColumnClass(model.Employee.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        jScrollPane1.setViewportView(jTable1);

        jPanel4.setBackground(new java.awt.Color(77, 78, 80));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jComboBox2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, employeeList, jComboBox2);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.eid}"), jComboBox2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.amount}"), jTextField2, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Bill amount :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Inserted by (Emp. ID) :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Bill type :");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Bill month :");

        jButton9.setBackground(new java.awt.Color(0, 102, 51));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Insert");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(160, 31, 38));
        jButton15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Delete");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.month}"), jTextField1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, billtypeList, jComboBox1);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.type}"), jComboBox1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jButton14.setBackground(new java.awt.Color(28, 47, 123));
        jButton14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Update");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jTextField4.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.pid}"), jTextField4, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Payment ID :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton9)
                        .addGap(63, 63, 63))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel14)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel17))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(jButton15)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField4)
                            .addComponent(jTextField1)
                            .addComponent(jComboBox1, 0, 149, Short.MAX_VALUE)
                            .addComponent(jTextField2)
                            .addComponent(jComboBox2, 0, 177, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox1, jComboBox2, jTextField1, jTextField2});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(64, 64, 64)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(60, 60, 60)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(86, 86, 86)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton14)
                    .addComponent(jButton15))
                .addGap(33, 33, 33))
        );

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout pmInsertBillPaymentsLayout = new javax.swing.GroupLayout(pmInsertBillPayments);
        pmInsertBillPayments.setLayout(pmInsertBillPaymentsLayout);
        pmInsertBillPaymentsLayout.setHorizontalGroup(
            pmInsertBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmInsertBillPaymentsLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(617, Short.MAX_VALUE))
        );
        pmInsertBillPaymentsLayout.setVerticalGroup(
            pmInsertBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmInsertBillPaymentsLayout.createSequentialGroup()
                .addGroup(pmInsertBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pmInsertBillPaymentsLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addGroup(pmInsertBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pmInsertBillPaymentsLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6615, Short.MAX_VALUE))
        );

        jPanel3.add(pmInsertBillPayments, "card10");

        DMjPanel9.setBackground(new java.awt.Color(153, 153, 153));

        dmjButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add vehicles.png"))); // NOI18N
        dmjButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmjButton5ActionPerformed(evt);
            }
        });

        dmjButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/view reports for sales and inventory  and other all managements.png"))); // NOI18N
        dmjButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmjButton4ActionPerformed(evt);
            }
        });

        dmjButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delivery details.png"))); // NOI18N
        dmjButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmjButton3ActionPerformed(evt);
            }
        });

        dmjButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add delivery.png"))); // NOI18N
        dmjButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmjButton2ActionPerformed(evt);
            }
        });

        dmjButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/vehicle details.png"))); // NOI18N
        dmjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmjButton1ActionPerformed(evt);
            }
        });

        dmjLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        dmjLabel1.setText("Dellivery Management");

        javax.swing.GroupLayout DMjPanel9Layout = new javax.swing.GroupLayout(DMjPanel9);
        DMjPanel9.setLayout(DMjPanel9Layout);
        DMjPanel9Layout.setHorizontalGroup(
            DMjPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DMjPanel9Layout.createSequentialGroup()
                .addGap(238, 238, 238)
                .addGroup(DMjPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmjButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DMjPanel9Layout.createSequentialGroup()
                        .addComponent(dmjButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(DMjPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DMjPanel9Layout.createSequentialGroup()
                                .addGap(144, 144, 144)
                                .addComponent(dmjButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(117, 117, 117)
                                .addComponent(dmjButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(95, 95, 95)
                                .addComponent(dmjButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DMjPanel9Layout.createSequentialGroup()
                                .addGap(230, 230, 230)
                                .addComponent(dmjLabel1)))))
                .addContainerGap(561, Short.MAX_VALUE))
        );
        DMjPanel9Layout.setVerticalGroup(
            DMjPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DMjPanel9Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(dmjLabel1)
                .addGap(116, 116, 116)
                .addGroup(DMjPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmjButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmjButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmjButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmjButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addComponent(dmjButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6614, Short.MAX_VALUE))
        );

        jPanel3.add(DMjPanel9, "card7");

        EMjPanel4.setBackground(new java.awt.Color(153, 153, 153));
        EMjPanel4.setPreferredSize(new java.awt.Dimension(1143, 707));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel4.setText("Employee management");

        attendanceEmpNavBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/attendance emp.png"))); // NOI18N
        attendanceEmpNavBtn.setText("Attendance");
        attendanceEmpNavBtn.setPreferredSize(new java.awt.Dimension(232, 232));
        attendanceEmpNavBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attendanceEmpNavBtnActionPerformed(evt);
            }
        });

        leaveEmpNavBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/leave request.png"))); // NOI18N
        leaveEmpNavBtn.setText("Leave Request");
        leaveEmpNavBtn.setPreferredSize(new java.awt.Dimension(232, 232));
        leaveEmpNavBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveEmpNavBtnActionPerformed(evt);
            }
        });

        regEmpNavBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reg employee.png"))); // NOI18N
        regEmpNavBtn.setText("Register Employee");
        regEmpNavBtn.setPreferredSize(new java.awt.Dimension(232, 232));
        regEmpNavBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regEmpNavBtnActionPerformed(evt);
            }
        });

        addEmpCatNavBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add category.png"))); // NOI18N
        addEmpCatNavBtn.setText("Add catergory");
        addEmpCatNavBtn.setPreferredSize(new java.awt.Dimension(232, 232));
        addEmpCatNavBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpCatNavBtnActionPerformed(evt);
            }
        });

        empLeaveMgmtNavBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/leave managemnt.png"))); // NOI18N
        empLeaveMgmtNavBtn.setText("Leave Mangement");
        empLeaveMgmtNavBtn.setPreferredSize(new java.awt.Dimension(232, 232));
        empLeaveMgmtNavBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empLeaveMgmtNavBtnActionPerformed(evt);
            }
        });

        empReports.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/view reports for sales and inventory  and other all managements.png"))); // NOI18N
        empReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empReportsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EMjPanel4Layout = new javax.swing.GroupLayout(EMjPanel4);
        EMjPanel4.setLayout(EMjPanel4Layout);
        EMjPanel4Layout.setHorizontalGroup(
            EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EMjPanel4Layout.createSequentialGroup()
                .addGroup(EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EMjPanel4Layout.createSequentialGroup()
                        .addGap(479, 479, 479)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EMjPanel4Layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addGroup(EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(EMjPanel4Layout.createSequentialGroup()
                                .addComponent(addEmpCatNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(empReports, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EMjPanel4Layout.createSequentialGroup()
                                .addComponent(regEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(142, 142, 142)
                                .addComponent(leaveEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(160, 160, 160)
                        .addComponent(empLeaveMgmtNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(attendanceEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        EMjPanel4Layout.setVerticalGroup(
            EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EMjPanel4Layout.createSequentialGroup()
                .addGroup(EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EMjPanel4Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EMjPanel4Layout.createSequentialGroup()
                        .addGap(257, 257, 257)
                        .addGroup(EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(regEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(leaveEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveMgmtNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(attendanceEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(113, 113, 113)
                .addGroup(EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addEmpCatNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empReports, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(EMjPanel4, "card2");

        attendanceEmpCard.setBackground(new java.awt.Color(153, 153, 153));

        empDateLabel.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
        empDateLabel.setForeground(new java.awt.Color(255, 255, 255));
        empDateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        empSignOutBtn.setBackground(new java.awt.Color(160, 31, 38));
        empSignOutBtn.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empSignOutBtn.setForeground(new java.awt.Color(255, 255, 255));
        empSignOutBtn.setText("Sign Out");
        empSignOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empSignOutBtnActionPerformed(evt);
            }
        });

        empSignInBtn.setBackground(new java.awt.Color(0, 102, 51));
        empSignInBtn.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empSignInBtn.setForeground(new java.awt.Color(255, 255, 255));
        empSignInBtn.setText("Sign In");
        empSignInBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empSignInBtnActionPerformed(evt);
            }
        });

        empAttendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(empAttendanceTable);

        javax.swing.GroupLayout attendanceEmpCardLayout = new javax.swing.GroupLayout(attendanceEmpCard);
        attendanceEmpCard.setLayout(attendanceEmpCardLayout);
        attendanceEmpCardLayout.setHorizontalGroup(
            attendanceEmpCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attendanceEmpCardLayout.createSequentialGroup()
                .addGroup(attendanceEmpCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(attendanceEmpCardLayout.createSequentialGroup()
                        .addGap(567, 567, 567)
                        .addComponent(empDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(attendanceEmpCardLayout.createSequentialGroup()
                        .addGap(537, 537, 537)
                        .addGroup(attendanceEmpCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(attendanceEmpCardLayout.createSequentialGroup()
                                .addComponent(empSignInBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(empSignOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(810, Short.MAX_VALUE))
        );
        attendanceEmpCardLayout.setVerticalGroup(
            attendanceEmpCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attendanceEmpCardLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(empDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(attendanceEmpCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empSignInBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empSignOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(attendanceEmpCard, "attendanceEmpCard");

        addCatergoryPanel.setBackground(new java.awt.Color(153, 153, 153));

        empAddTypeLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empAddTypeLabel.setForeground(new java.awt.Color(255, 255, 255));
        empAddTypeLabel.setText("Type");

        empTypeIdBox.setEditable(false);
        empTypeIdBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        empSalaryLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empSalaryLabel.setForeground(new java.awt.Color(255, 255, 255));
        empSalaryLabel.setText("Basic Salary");

        empSalaryBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        empLeaveLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empLeaveLabel.setForeground(new java.awt.Color(255, 255, 255));
        empLeaveLabel.setText("Annual Leave");

        empLeaveBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        updateEmpTypeBtn.setBackground(new java.awt.Color(28, 47, 123));
        updateEmpTypeBtn.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        updateEmpTypeBtn.setForeground(new java.awt.Color(255, 255, 255));
        updateEmpTypeBtn.setText("Update");
        updateEmpTypeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEmpTypeBtnActionPerformed(evt);
            }
        });

        deleteEmpTypeBtn.setBackground(new java.awt.Color(160, 31, 38));
        deleteEmpTypeBtn.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        deleteEmpTypeBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteEmpTypeBtn.setText("Delete");
        deleteEmpTypeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEmpTypeBtnActionPerformed(evt);
            }
        });

        addEmpTypeBtn.setBackground(new java.awt.Color(0, 102, 51));
        addEmpTypeBtn.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        addEmpTypeBtn.setForeground(new java.awt.Color(255, 255, 255));
        addEmpTypeBtn.setText("Add");
        addEmpTypeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpTypeBtnActionPerformed(evt);
            }
        });

        empCatergoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        empCatergoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empCatergoryTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(empCatergoryTable);

        empTypeIdLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empTypeIdLabel.setForeground(new java.awt.Color(255, 255, 255));
        empTypeIdLabel.setText("Type ID");

        empTypeBox1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout addCatergoryPanelLayout = new javax.swing.GroupLayout(addCatergoryPanel);
        addCatergoryPanel.setLayout(addCatergoryPanelLayout);
        addCatergoryPanelLayout.setHorizontalGroup(
            addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                            .addComponent(empSalaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(empSalaryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                            .addComponent(empAddTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(empTypeBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                            .addComponent(empLeaveLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(empLeaveBox, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                        .addComponent(addEmpTypeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(updateEmpTypeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(deleteEmpTypeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                        .addComponent(empTypeIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(empTypeIdBox, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156)))
                .addGap(234, 234, 234)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(204, 204, 204)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(547, Short.MAX_VALUE))
        );
        addCatergoryPanelLayout.setVerticalGroup(
            addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empTypeIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empTypeIdBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empAddTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empTypeBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empSalaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empSalaryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empLeaveLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addEmpTypeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateEmpTypeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteEmpTypeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3.add(addCatergoryPanel, "addCatergoryPanel");

        empLeaveMgmtPanel.setBackground(new java.awt.Color(153, 153, 153));

        empReasonLabel_mgmt.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empReasonLabel_mgmt.setForeground(new java.awt.Color(255, 255, 255));
        empReasonLabel_mgmt.setText("Reason");

        empReasonText_mgmt.setEditable(false);
        empReasonText_mgmt.setColumns(20);
        empReasonText_mgmt.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empReasonText_mgmt.setRows(5);
        jScrollPane6.setViewportView(empReasonText_mgmt);

        empApproveRequestBtn_mgmt.setBackground(new java.awt.Color(0, 102, 51));
        empApproveRequestBtn_mgmt.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empApproveRequestBtn_mgmt.setForeground(new java.awt.Color(255, 255, 255));
        empApproveRequestBtn_mgmt.setText("Approve Request");
        empApproveRequestBtn_mgmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empApproveRequestBtn_mgmtActionPerformed(evt);
            }
        });

        empstartDateBox_mgmt.setEditable(false);
        empstartDateBox_mgmt.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        empstartDateLabel_mgmt.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empstartDateLabel_mgmt.setForeground(new java.awt.Color(255, 255, 255));
        empstartDateLabel_mgmt.setText("Start Date");

        empEndDateBox_mgmt.setEditable(false);
        empEndDateBox_mgmt.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        empEndDateLabel_mgmt.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empEndDateLabel_mgmt.setForeground(new java.awt.Color(255, 255, 255));
        empEndDateLabel_mgmt.setText("End Date");

        empDaysLabel_mgmt.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empDaysLabel_mgmt.setForeground(new java.awt.Color(255, 255, 255));
        empDaysLabel_mgmt.setText("Number of Days");

        empDaysBox_mgmt.setEditable(false);
        empDaysBox_mgmt.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        empID_mgmt1.setEditable(false);
        empID_mgmt1.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        empstartDateLabel_mgmt1.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empstartDateLabel_mgmt1.setForeground(new java.awt.Color(255, 255, 255));
        empstartDateLabel_mgmt1.setText("Employee ID");

        empEndDateBox_mgmt1.setEditable(false);
        empEndDateBox_mgmt1.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        empEndDateLabel_mgmt1.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empEndDateLabel_mgmt1.setForeground(new java.awt.Color(255, 255, 255));
        empEndDateLabel_mgmt1.setText("Employee Name");

        empLeaveRequestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        empLeaveRequestTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empLeaveRequestTableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(empLeaveRequestTable);

        empRejectRequestBtn_mgmt.setBackground(new java.awt.Color(204, 0, 0));
        empRejectRequestBtn_mgmt.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empRejectRequestBtn_mgmt.setForeground(new java.awt.Color(255, 255, 255));
        empRejectRequestBtn_mgmt.setText("Reject Request");
        empRejectRequestBtn_mgmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empRejectRequestBtn_mgmtActionPerformed(evt);
            }
        });

        empLeaveReqIDLabel_mgmt.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empLeaveReqIDLabel_mgmt.setForeground(new java.awt.Color(255, 255, 255));
        empLeaveReqIDLabel_mgmt.setText("Request ID");

        empLeaveReqID_mgmt.setEditable(false);
        empLeaveReqID_mgmt.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout empLeaveMgmtPanelLayout = new javax.swing.GroupLayout(empLeaveMgmtPanel);
        empLeaveMgmtPanel.setLayout(empLeaveMgmtPanelLayout);
        empLeaveMgmtPanelLayout.setHorizontalGroup(
            empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(empEndDateLabel_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empstartDateLabel_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveReqIDLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(empEndDateBox_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveReqID_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empID_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(empReasonLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empDaysLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empEndDateLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empstartDateLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(empEndDateBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empDaysBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empstartDateBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                                    .addComponent(empApproveRequestBtn_mgmt)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(empRejectRequestBtn_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(499, Short.MAX_VALUE))
        );
        empLeaveMgmtPanelLayout.setVerticalGroup(
            empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empLeaveReqIDLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveReqID_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                                .addComponent(empID_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(empEndDateBox_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                                .addComponent(empstartDateLabel_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(empEndDateLabel_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                                .addComponent(empstartDateLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(empEndDateLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(empDaysLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                                .addComponent(empstartDateBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(empEndDateBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(empDaysBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(empReasonLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empApproveRequestBtn_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empRejectRequestBtn_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3.add(empLeaveMgmtPanel, "empLeaveMgmtPanel");

        smGenerateBills.setBackground(new java.awt.Color(153, 153, 153));

        smjLabel4.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smjLabel4.setForeground(new java.awt.Color(255, 255, 255));
        smjLabel4.setText("Enter Customer Mobile Number");

        custp.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        custp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                custpKeyReleased(evt);
            }
        });

        smjLabel5.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smjLabel5.setForeground(new java.awt.Color(255, 255, 255));
        smjLabel5.setText("Enter Payment Method for Bill");

        btnNxtGenerateBill.setBackground(new java.awt.Color(0, 102, 51));
        btnNxtGenerateBill.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        btnNxtGenerateBill.setForeground(new java.awt.Color(255, 255, 255));
        btnNxtGenerateBill.setText("Proceed");
        btnNxtGenerateBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNxtGenerateBillActionPerformed(evt);
            }
        });

        custpaymentmethod.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        custpaymentmethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Cheque", "Card" }));

        smCustIdStatus.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        smCustIdStatus.setForeground(new java.awt.Color(255, 0, 0));

        smjLabel6.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smjLabel6.setForeground(new java.awt.Color(255, 255, 255));
        smjLabel6.setText("Enter  Refunds");

        smdisref.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smdisref.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                smdisrefKeyReleased(evt);
            }
        });

        smjLabel7.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smjLabel7.setForeground(new java.awt.Color(255, 255, 255));
        smjLabel7.setText("Enter Item Id");

        smitemid.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        smjLabel8.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smjLabel8.setForeground(new java.awt.Color(255, 255, 255));
        smjLabel8.setText("Enter Item Qty");

        smitemqty.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smitemqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                smitemqtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                smitemqtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                smitemqtyKeyTyped(evt);
            }
        });

        smbtnadditem.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smbtnadditem.setText("Add Item");
        smbtnadditem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smbtnadditemActionPerformed(evt);
            }
        });

        smbtndeleteitem.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smbtndeleteitem.setText("Delete Item");
        smbtndeleteitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smbtndeleteitemActionPerformed(evt);
            }
        });

        smbilltable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item Id", "Item Name", "Qty", "Amount"
            }
        ));
        smbilltable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                smbilltableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(smbilltable);

        smreset.setBackground(new java.awt.Color(160, 31, 38));
        smreset.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smreset.setForeground(new java.awt.Color(255, 255, 255));
        smreset.setText("Reset");
        smreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smresetActionPerformed(evt);
            }
        });

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout smGenerateBillsLayout = new javax.swing.GroupLayout(smGenerateBills);
        smGenerateBills.setLayout(smGenerateBillsLayout);
        smGenerateBillsLayout.setHorizontalGroup(
            smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(smdisrefstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(smqtystatus, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                                .addComponent(smjLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(525, 525, 525))
                            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                                .addComponent(smjLabel4)
                                .addGap(51, 51, 51)
                                .addComponent(custp, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(260, 260, 260))
                            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                                        .addComponent(smreset, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 377, Short.MAX_VALUE)
                                        .addComponent(btnNxtGenerateBill, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, smGenerateBillsLayout.createSequentialGroup()
                                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(smjLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(smjLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(smjLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(smitemid, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                                    .addComponent(smitemqty))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(smbtndeleteitem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(smbtnadditem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(custpaymentmethod, 0, 300, Short.MAX_VALUE)
                                            .addComponent(smdisref))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, smGenerateBillsLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(smCustIdStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165)))
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(776, 776, 776))
        );
        smGenerateBillsLayout.setVerticalGroup(
            smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(smjLabel4)
                                    .addComponent(custp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(smCustIdStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 109, 109)
                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(smbtnadditem)
                                    .addComponent(smitemid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(smjLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(smjLabel8)
                                    .addComponent(smitemqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(smbtndeleteitem))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(smqtystatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(smjLabel5)
                                    .addComponent(custpaymentmethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(smdisref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(smjLabel6))
                                .addGap(71, 71, 71)
                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnNxtGenerateBill)
                                    .addComponent(smreset)))))
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(349, 349, 349)
                .addComponent(smdisrefstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );

        jPanel3.add(smGenerateBills, "smGenerateBills");

        smReturnHandle.setBackground(new java.awt.Color(153, 153, 153));

        smviewreturnstable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Sales Id", "Item Id", "ItemQty"
            }
        ));
        smviewreturnstable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                smviewreturnstableMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(smviewreturnstable);

        smenterbillid.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smenterbillid.setForeground(new java.awt.Color(255, 255, 255));
        smenterbillid.setText("Enter Bill Id");

        smshowitemid.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smshowitemid.setForeground(new java.awt.Color(255, 255, 255));
        smshowitemid.setText("Item Id");

        smshowitemname.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smshowitemname.setForeground(new java.awt.Color(255, 255, 255));
        smshowitemname.setText("Item Qty");

        smtxtfieldenterbillid.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        smtxtitemid.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        smtxtitemqty.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        smbtnsearchbillid.setBackground(new java.awt.Color(0, 102, 51));
        smbtnsearchbillid.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smbtnsearchbillid.setForeground(new java.awt.Color(255, 255, 255));
        smbtnsearchbillid.setText("Search");
        smbtnsearchbillid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smbtnsearchbillidActionPerformed(evt);
            }
        });

        smbtndeleteitemreturns.setBackground(new java.awt.Color(160, 31, 38));
        smbtndeleteitemreturns.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smbtndeleteitemreturns.setForeground(new java.awt.Color(255, 255, 255));
        smbtndeleteitemreturns.setText("Delete");
        smbtndeleteitemreturns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smbtndeleteitemreturnsActionPerformed(evt);
            }
        });

        smsalesidreturns.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        smsalesidreturns.setForeground(new java.awt.Color(255, 255, 255));
        smsalesidreturns.setText("Sales Id");

        smsalesidreturnsbox.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout smReturnHandleLayout = new javax.swing.GroupLayout(smReturnHandle);
        smReturnHandle.setLayout(smReturnHandleLayout);
        smReturnHandleLayout.setHorizontalGroup(
            smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smReturnHandleLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(smenterbillid, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(smshowitemid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(smshowitemname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(smsalesidreturns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(smbtndeleteitemreturns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(smbtnsearchbillid, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(smtxtfieldenterbillid)
                    .addComponent(smtxtitemid)
                    .addComponent(smtxtitemqty)
                    .addComponent(smsalesidreturnsbox))
                .addGap(108, 108, 108)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(945, Short.MAX_VALUE))
        );
        smReturnHandleLayout.setVerticalGroup(
            smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smReturnHandleLayout.createSequentialGroup()
                .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(smReturnHandleLayout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(smReturnHandleLayout.createSequentialGroup()
                                .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(smenterbillid, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(smtxtfieldenterbillid, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(smbtnsearchbillid, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102)
                                .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(smsalesidreturns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(smsalesidreturnsbox, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(smtxtitemid, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(smshowitemid, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(smtxtitemqty, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(smshowitemname, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addComponent(smbtndeleteitemreturns, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(smReturnHandleLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6720, Short.MAX_VALUE))
        );

        jPanel3.add(smReturnHandle, "smReturnHandle");

        smGenerateBills2.setBackground(new java.awt.Color(153, 153, 153));

        smjLabel9.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        smjLabel9.setText("Total Bill Amount");

        smtotbillamt.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        smtotbillamt.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                smtotbillamtMouseMoved(evt);
            }
        });

        smjLabel10.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        smjLabel10.setText("Cash / Amount Paid");

        smcashamtpaid.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N

        smjLabelch.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        smjLabelch.setText("Change ");

        smchangedis.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        smchangedis.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                smchangedisMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout smGenerateBills2Layout = new javax.swing.GroupLayout(smGenerateBills2);
        smGenerateBills2.setLayout(smGenerateBills2Layout);
        smGenerateBills2Layout.setHorizontalGroup(
            smGenerateBills2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smGenerateBills2Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(smGenerateBills2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(smjLabelch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(smjLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(smjLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(66, 66, 66)
                .addGroup(smGenerateBills2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(smtotbillamt)
                    .addComponent(smcashamtpaid)
                    .addComponent(smchangedis, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
                .addContainerGap(1017, Short.MAX_VALUE))
        );
        smGenerateBills2Layout.setVerticalGroup(
            smGenerateBills2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smGenerateBills2Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(smGenerateBills2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(smtotbillamt)
                    .addComponent(smjLabel10))
                .addGap(74, 74, 74)
                .addGroup(smGenerateBills2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(smcashamtpaid, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(smjLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(smGenerateBills2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(smGenerateBills2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(smjLabelch, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, smGenerateBills2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(smchangedis, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6954, Short.MAX_VALUE))
        );

        jPanel3.add(smGenerateBills2, "smGenerateBills2");

        pmSearchBillPayments.setBackground(new java.awt.Color(153, 153, 153));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Search");

        jTextField3.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, billpaymentList, jTable2);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${amount}"));
        columnBinding.setColumnName("Amount");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${eid}"));
        columnBinding.setColumnName("Eid");
        columnBinding.setColumnClass(model.Employee.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${month}"));
        columnBinding.setColumnName("Month");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${pid}"));
        columnBinding.setColumnName("Pid");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${type}"));
        columnBinding.setColumnName("Type");
        columnBinding.setColumnClass(model.Billtype.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        jScrollPane11.setViewportView(jTable2);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setText("By month");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setText("By type");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jRadioButton3.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton3.setText("By employee id");

        javax.swing.GroupLayout pmSearchBillPaymentsLayout = new javax.swing.GroupLayout(pmSearchBillPayments);
        pmSearchBillPayments.setLayout(pmSearchBillPaymentsLayout);
        pmSearchBillPaymentsLayout.setHorizontalGroup(
            pmSearchBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmSearchBillPaymentsLayout.createSequentialGroup()
                .addGap(371, 371, 371)
                .addGroup(pmSearchBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pmSearchBillPaymentsLayout.createSequentialGroup()
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(78, 78, 78)
                        .addComponent(jRadioButton2)
                        .addGap(80, 80, 80)
                        .addComponent(jRadioButton3))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pmSearchBillPaymentsLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1057, Short.MAX_VALUE))
        );
        pmSearchBillPaymentsLayout.setVerticalGroup(
            pmSearchBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmSearchBillPaymentsLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(pmSearchBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addGroup(pmSearchBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addGap(99, 99, 99)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6716, Short.MAX_VALUE))
        );

        jPanel3.add(pmSearchBillPayments, "pmSearchBillPayments");

        cmAddCus.setBackground(new java.awt.Color(153, 153, 153));

        head_add_cus.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        head_add_cus.setText("Add Customers");

        name_cus.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        name_cus.setText("Name");

        phone_cus.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        phone_cus.setText("Address");

        address_cus_ad.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        address_cus_ad.setText("Phone");

        email_cus_ad.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        email_cus_ad.setText("Email");

        reset_cus_add.setBackground(new java.awt.Color(255, 51, 51));
        reset_cus_add.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        reset_cus_add.setForeground(new java.awt.Color(255, 255, 255));
        reset_cus_add.setText("Reset");
        reset_cus_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_cus_addActionPerformed(evt);
            }
        });

        submit_cuss_add.setBackground(new java.awt.Color(0, 102, 51));
        submit_cuss_add.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        submit_cuss_add.setText("Submit");
        submit_cuss_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_cuss_addActionPerformed(evt);
            }
        });

        cusEmailAdd.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N

        cusNameAdd.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N

        cusPhoneAdd.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N

        cusAddAdd.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N

        message.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        message.setForeground(new java.awt.Color(102, 51, 255));

        ID_Val.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ID_Val.setForeground(new java.awt.Color(255, 0, 51));

        phone_val.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        phone_val.setForeground(new java.awt.Color(255, 51, 51));

        name_val.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        name_val.setForeground(new java.awt.Color(255, 51, 51));

        address_val.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        address_val.setForeground(new java.awt.Color(255, 51, 51));

        email_val.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        email_val.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout cmAddCusLayout = new javax.swing.GroupLayout(cmAddCus);
        cmAddCus.setLayout(cmAddCusLayout);
        cmAddCusLayout.setHorizontalGroup(
            cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmAddCusLayout.createSequentialGroup()
                .addGap(400, 400, 400)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name_cus)
                    .addComponent(address_cus_ad)
                    .addComponent(phone_cus)
                    .addComponent(email_cus_ad))
                .addGap(75, 75, 75)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(head_add_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(cmAddCusLayout.createSequentialGroup()
                        .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(cmAddCusLayout.createSequentialGroup()
                                .addComponent(reset_cus_add, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(81, 81, 81)
                                .addComponent(submit_cuss_add, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cusNameAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cusPhoneAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cusEmailAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cusAddAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(email_val)
                            .addComponent(phone_val)
                            .addComponent(address_val)
                            .addComponent(name_val)
                            .addComponent(ID_Val))))
                .addContainerGap(1049, Short.MAX_VALUE))
        );
        cmAddCusLayout.setVerticalGroup(
            cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmAddCusLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(head_add_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146)
                .addComponent(ID_Val)
                .addGap(18, 18, 18)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cusNameAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name_cus)
                    .addComponent(name_val))
                .addGap(18, 18, 18)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cusPhoneAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address_cus_ad)
                    .addComponent(phone_val))
                .addGap(18, 18, 18)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phone_cus)
                    .addComponent(cusAddAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address_val))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cusEmailAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email_cus_ad)
                    .addComponent(email_val))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reset_cus_add, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submit_cuss_add, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(6769, Short.MAX_VALUE))
        );

        jPanel3.add(cmAddCus, "cmAddCus");

        cmUDCus.setBackground(new java.awt.Color(153, 153, 153));

        phoneBoxCus.setFont(new java.awt.Font("SansSerif", 0, 30)); // NOI18N

        emailBoxCus.setFont(new java.awt.Font("SansSerif", 0, 30)); // NOI18N

        ID_cus.setFont(new java.awt.Font("SansSerif", 0, 30)); // NOI18N
        ID_cus.setText("ID");

        manage_cus.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        manage_cus.setText("Manage Customers");

        cancel_cus.setBackground(new java.awt.Color(255, 173, 67));
        cancel_cus.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cancel_cus.setForeground(new java.awt.Color(51, 51, 0));
        cancel_cus.setText("Cancel");
        cancel_cus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_cusActionPerformed(evt);
            }
        });

        address_cus.setFont(new java.awt.Font("SansSerif", 0, 30)); // NOI18N
        address_cus.setText("Address");

        name_cus_u.setFont(new java.awt.Font("SansSerif", 0, 30)); // NOI18N

        phone_cus_u.setFont(new java.awt.Font("SansSerif", 0, 30)); // NOI18N
        phone_cus_u.setText("Phone");

        email_cus_U.setFont(new java.awt.Font("SansSerif", 0, 30)); // NOI18N
        email_cus_U.setText("Email");

        adress_cus_u.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        name_cus_t.setFont(new java.awt.Font("SansSerif", 0, 30)); // NOI18N
        name_cus_t.setText("Name");

        delete_cus_U.setBackground(new java.awt.Color(255, 51, 51));
        delete_cus_U.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        delete_cus_U.setForeground(new java.awt.Color(255, 255, 255));
        delete_cus_U.setText("Delete");
        delete_cus_U.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_cus_UActionPerformed(evt);
            }
        });

        update_cus_u.setBackground(new java.awt.Color(51, 51, 255));
        update_cus_u.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        update_cus_u.setForeground(new java.awt.Color(255, 255, 255));
        update_cus_u.setText("Update");
        update_cus_u.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_cus_uActionPerformed(evt);
            }
        });

        cus_view_table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cus_view_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        cus_view_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cus_view_tableMouseClicked(evt);
            }
        });
        cus_view_scroll.setViewportView(cus_view_table);

        labCusID.setFont(new java.awt.Font("SansSerif", 0, 30)); // NOI18N
        labCusID.setForeground(new java.awt.Color(255, 0, 51));
        labCusID.setText("Customer ID");

        name_val_U.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        name_val_U.setForeground(new java.awt.Color(255, 0, 0));

        phone_val_U1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        phone_val_U1.setForeground(new java.awt.Color(255, 0, 0));

        address_val_U2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        address_val_U2.setForeground(new java.awt.Color(255, 0, 0));

        email_val_U3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        email_val_U3.setForeground(new java.awt.Color(255, 0, 0));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton18.setText("Search");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cmUDCusLayout = new javax.swing.GroupLayout(cmUDCus);
        cmUDCus.setLayout(cmUDCusLayout);
        cmUDCusLayout.setHorizontalGroup(
            cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmUDCusLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(cmUDCusLayout.createSequentialGroup()
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ID_cus)
                            .addComponent(name_cus_t)
                            .addComponent(email_cus_U)
                            .addComponent(phone_cus_u)
                            .addComponent(address_cus))
                        .addGap(75, 75, 75)
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(cmUDCusLayout.createSequentialGroup()
                                .addComponent(cancel_cus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(delete_cus_U, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(update_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(name_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneBoxCus, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailBoxCus, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adress_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labCusID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name_val_U)
                            .addComponent(email_val_U3)
                            .addComponent(address_val_U2)
                            .addComponent(phone_val_U1))
                        .addGap(38, 38, 38)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cus_view_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(cmUDCusLayout.createSequentialGroup()
                                .addComponent(Cus_search, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cmUDCusLayout.createSequentialGroup()
                        .addComponent(manage_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1442, Short.MAX_VALUE))))
        );
        cmUDCusLayout.setVerticalGroup(
            cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmUDCusLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(manage_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cmUDCusLayout.createSequentialGroup()
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Cus_search, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cmUDCusLayout.createSequentialGroup()
                                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ID_cus)
                                    .addComponent(labCusID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(name_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(name_cus_t)
                                    .addComponent(name_val_U))
                                .addGap(18, 18, 18)
                                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(phoneBoxCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(phone_cus_u)
                                    .addComponent(phone_val_U1))
                                .addGap(18, 18, 18)
                                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(address_cus)
                                    .addComponent(adress_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(address_val_U2))
                                .addGap(18, 18, 18)
                                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(emailBoxCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(email_cus_U)
                                    .addComponent(email_val_U3))
                                .addGap(59, 59, 59)
                                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cancel_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(delete_cus_U, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(update_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cus_view_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(6831, Short.MAX_VALUE))
                    .addGroup(cmUDCusLayout.createSequentialGroup()
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel3.add(cmUDCus, "cmUDCus");

        IMsearch.setBackground(new java.awt.Color(153, 153, 153));

        IM_Stable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(IM_Stable);

        IM_serch_by.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_serch_by.setText("search by : ");

        IM_key.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        IM_Sby.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        IM_Sby.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "id", "name", "brand", "less than qty", "greater than qty" }));

        IM_search_btn.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_search_btn.setText("search");
        IM_search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_search_btnActionPerformed(evt);
            }
        });

        IM_SEARCH_txt.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        IM_SEARCH_txt.setForeground(new java.awt.Color(255, 255, 255));
        IM_SEARCH_txt.setText("SEARCH ITEM");

        javax.swing.GroupLayout IMsearchLayout = new javax.swing.GroupLayout(IMsearch);
        IMsearch.setLayout(IMsearchLayout);
        IMsearchLayout.setHorizontalGroup(
            IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMsearchLayout.createSequentialGroup()
                .addGroup(IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IMsearchLayout.createSequentialGroup()
                        .addGap(525, 525, 525)
                        .addComponent(IM_serch_by)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(IM_Sby, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(IM_key, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(IM_search_btn))
                    .addGroup(IMsearchLayout.createSequentialGroup()
                        .addGap(311, 311, 311)
                        .addGroup(IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IM_SEARCH_txt)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(702, Short.MAX_VALUE))
        );
        IMsearchLayout.setVerticalGroup(
            IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMsearchLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(IM_SEARCH_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IMsearchLayout.createSequentialGroup()
                        .addComponent(IM_Sby, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(IM_serch_by)
                        .addComponent(IM_key, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(IM_search_btn)))
                .addGap(52, 52, 52)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6753, Short.MAX_VALUE))
        );

        jPanel3.add(IMsearch, "IM_search");

        IMupdate.setBackground(new java.awt.Color(153, 153, 153));

        IM_name_lbl.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_name_lbl.setText("Name");

        IM_type_lbl.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_type_lbl.setText("Brand");

        IM_qty_lbl.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_qty_lbl.setText("Qty");

        IM_bprice_lbl.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_bprice_lbl.setText("Buying price");

        IM_sprice_lbl.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_sprice_lbl.setText("Selling price");

        IM_ID_lbl.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_ID_lbl.setText("ID");

        I_ID.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        I_Uname.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        I_Uqty.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        I_Utype.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        I_USprice.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        I_UBprice.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        IM_update_btn.setBackground(new java.awt.Color(28, 47, 123));
        IM_update_btn.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_update_btn.setText("UPDATE");
        IM_update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_update_btnActionPerformed(evt);
            }
        });

        IM_Utable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        IM_Utable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IM_UtableMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(IM_Utable);

        IM_delete_btn.setBackground(new java.awt.Color(160, 31, 38));
        IM_delete_btn.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_delete_btn.setText("DELETE");
        IM_delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_delete_btnActionPerformed(evt);
            }
        });

        IM_U_qty_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_U_Bprice_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_U_Sprice_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_U_update.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        IM_U_update.setForeground(new java.awt.Color(0, 204, 51));

        IM_U_name_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_U_brand_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_U_delete.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        IM_U_delete.setForeground(new java.awt.Color(255, 0, 0));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        IM_UPDATE_txt.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        IM_UPDATE_txt.setForeground(new java.awt.Color(255, 255, 255));
        IM_UPDATE_txt.setText("UPDATE / DELETE ITEM");

        javax.swing.GroupLayout IMupdateLayout = new javax.swing.GroupLayout(IMupdate);
        IMupdate.setLayout(IMupdateLayout);
        IMupdateLayout.setHorizontalGroup(
            IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMupdateLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IM_UPDATE_txt)
                    .addGroup(IMupdateLayout.createSequentialGroup()
                        .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(IMupdateLayout.createSequentialGroup()
                                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(IM_name_lbl)
                                    .addComponent(IM_sprice_lbl, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(IM_ID_lbl)
                                    .addComponent(IM_qty_lbl)
                                    .addComponent(IM_bprice_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IM_type_lbl)
                                    .addComponent(IM_update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(IM_U_name_error_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IM_U_Bprice_error_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(I_Uname)
                                    .addComponent(I_ID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(I_USprice)
                                    .addComponent(I_Uqty)
                                    .addComponent(I_UBprice)
                                    .addComponent(I_Utype)
                                    .addComponent(IM_U_brand_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_U_qty_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_U_Sprice_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_delete_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(IM_U_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IM_U_update, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(91, 91, 91)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(596, Short.MAX_VALUE))
        );
        IMupdateLayout.setVerticalGroup(
            IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMupdateLayout.createSequentialGroup()
                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IMupdateLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(IM_UPDATE_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(IM_ID_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(I_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(IMupdateLayout.createSequentialGroup()
                                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(I_Uname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_name_lbl))
                                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(IMupdateLayout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(IM_type_lbl))
                                    .addGroup(IMupdateLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(IM_U_name_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(I_Utype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IM_U_brand_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(I_Uqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_qty_lbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(IM_U_qty_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(I_UBprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_bprice_lbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IM_U_Bprice_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(I_USprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_sprice_lbl))
                                .addGap(6, 6, 6)
                                .addComponent(IM_U_Sprice_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(IM_update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(IM_U_update, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IM_U_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(IMupdateLayout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6643, Short.MAX_VALUE))
        );

        jPanel3.add(IMupdate, "IM_update");

        IMadd.setBackground(new java.awt.Color(153, 153, 153));

        IM_name.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_name.setText("Name");

        IM_type.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_type.setText("Brand");

        IM_qty.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_qty.setText("Qty");

        IM_bprice.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_bprice.setText("Buying price");

        IM_sprice.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_sprice.setText("Selling price");

        I_name.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        I_qty.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        I_type.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        I_Sprice.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        I_Bprice.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N

        IM_add_btn.setBackground(new java.awt.Color(0, 102, 51));
        IM_add_btn.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        IM_add_btn.setForeground(new java.awt.Color(255, 255, 255));
        IM_add_btn.setText("ADD");
        IM_add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_add_btnActionPerformed(evt);
            }
        });

        IM_Atable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane14.setViewportView(IM_Atable);

        IM_A_qty_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_A_Bprice_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_A_Sprice_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_A_success.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        IM_A_success.setForeground(new java.awt.Color(0, 204, 51));

        IM_A_name_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_A_brand_error_lbl.setForeground(new java.awt.Color(255, 0, 0));

        IM_ADD_txt.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        IM_ADD_txt.setForeground(new java.awt.Color(255, 255, 255));
        IM_ADD_txt.setText("ADD ITEM ");

        javax.swing.GroupLayout IMaddLayout = new javax.swing.GroupLayout(IMadd);
        IMadd.setLayout(IMaddLayout);
        IMaddLayout.setHorizontalGroup(
            IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMaddLayout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IMaddLayout.createSequentialGroup()
                        .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IM_name)
                            .addComponent(IM_type)
                            .addComponent(IM_bprice)
                            .addComponent(IM_sprice))
                        .addGap(32, 32, 32)
                        .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IM_A_Sprice_error_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(I_Sprice, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(IM_A_Bprice_error_lbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(I_Bprice, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(IM_A_qty_error_lbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(I_qty, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(IM_A_brand_error_lbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IM_A_name_error_lbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(I_name, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(I_type, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(IMaddLayout.createSequentialGroup()
                                    .addGap(218, 218, 218)
                                    .addComponent(IM_add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(IM_A_success, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(IM_ADD_txt)
                    .addComponent(IM_qty))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(918, 918, 918))
        );
        IMaddLayout.setVerticalGroup(
            IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMaddLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(IM_ADD_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IMaddLayout.createSequentialGroup()
                        .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IM_name)
                            .addComponent(I_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IM_A_name_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IM_type)
                            .addComponent(I_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addComponent(IM_A_brand_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IM_qty)
                            .addComponent(I_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IM_A_qty_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(IM_bprice)
                            .addComponent(I_Bprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IM_A_Bprice_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IM_sprice, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(I_Sprice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IM_A_Sprice_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(IM_add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(IM_A_success, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6681, Short.MAX_VALUE))
        );

        jPanel3.add(IMadd, "IM_add");

        sumPanel16.setBackground(new java.awt.Color(153, 153, 153));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel12.setText("Suppliers management");

        SuMOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/placeordershr.png"))); // NOI18N
        SuMOrder.setText("ORDER DETAILS");
        SuMOrder.setPreferredSize(new java.awt.Dimension(232, 232));
        SuMOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuMOrderActionPerformed(evt);
            }
        });

        SuMPlace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view reports for sales and inventory  and other all managements.png"))); // NOI18N
        SuMPlace.setText("REPORT");
        SuMPlace.setPreferredSize(new java.awt.Dimension(232, 232));
        SuMPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuMPlaceActionPerformed(evt);
            }
        });

        SuMAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add supplier.png"))); // NOI18N
        SuMAdd.setText("SUPPLIER DETAILS");
        SuMAdd.setPreferredSize(new java.awt.Dimension(232, 232));
        SuMAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuMAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sumPanel16Layout = new javax.swing.GroupLayout(sumPanel16);
        sumPanel16.setLayout(sumPanel16Layout);
        sumPanel16Layout.setHorizontalGroup(
            sumPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sumPanel16Layout.createSequentialGroup()
                .addGroup(sumPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sumPanel16Layout.createSequentialGroup()
                        .addGap(382, 382, 382)
                        .addComponent(jLabel12))
                    .addGroup(sumPanel16Layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(SuMAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(SuMOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(SuMPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(917, Short.MAX_VALUE))
        );
        sumPanel16Layout.setVerticalGroup(
            sumPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sumPanel16Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel12)
                .addGap(114, 114, 114)
                .addGroup(sumPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SuMAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuMOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuMPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(6942, Short.MAX_VALUE))
        );

        jPanel3.add(sumPanel16, "sumPanel16");

        dmAddDelivery_Panel_AddDelivery.setName("dmAddDelivery_Panel_AddDelivery"); // NOI18N

        dmAddDelivery_Label_Title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dmAddDelivery_Label_Title.setText("Add Delivery");

        dmAddDelivery_Panel_DeliveryInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Delivery Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmAddDelivery_Panel_DeliveryInfo.setName("dmAddDelivery_Panel_DeliveryInfo"); // NOI18N

        dmAddDelivery_Label_DeliveryID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_DeliveryID.setText("Delivery ID");

        dmAddDelivery_billID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dmAddDelivery_billID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        dmAddDelivery_billID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                dmAddDelivery_billIDPopupMenuWillBecomeVisible(evt);
            }
        });

        dmAddDelivery_Label_Billid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_Billid.setText("Bill ID");

        dmAddDelivery_Label_custName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_custName.setText("Customer Name");

        dmAddDelivery_custName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddDelivery_custNameActionPerformed(evt);
            }
        });

        dmAddDelivery_Label_date.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_date.setText("Date");

        dmAddDelivery_Label_time.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_time.setText("Time");

        dmAddDelivery_time.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddDelivery_timeActionPerformed(evt);
            }
        });
        dmAddDelivery_time.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dmAddDelivery_timeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dmAddDelivery_timeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dmAddDelivery_timeKeyTyped(evt);
            }
        });

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "am", "pm" }));

        dmAddDelivery_DeliveryID.setEditable(false);
        dmAddDelivery_DeliveryID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddDelivery_DeliveryIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmAddDelivery_Panel_DeliveryInfoLayout = new javax.swing.GroupLayout(dmAddDelivery_Panel_DeliveryInfo);
        dmAddDelivery_Panel_DeliveryInfo.setLayout(dmAddDelivery_Panel_DeliveryInfoLayout);
        dmAddDelivery_Panel_DeliveryInfoLayout.setHorizontalGroup(
            dmAddDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmAddDelivery_Label_DeliveryID)
                    .addComponent(dmAddDelivery_Label_Billid, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddDelivery_Label_custName)
                    .addComponent(dmAddDelivery_Label_date, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddDelivery_Label_time, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dmAddDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                        .addComponent(dmAddDelivery_time)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dmAddDelivery_DeliveryID)
                    .addComponent(dmAddDelivery_billID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dmAddDelivery_custName)
                    .addComponent(dmAddDelivery_date, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dmAddDelivery_Panel_DeliveryInfoLayout.setVerticalGroup(
            dmAddDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmAddDelivery_Label_DeliveryID, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddDelivery_DeliveryID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmAddDelivery_Label_Billid, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddDelivery_billID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmAddDelivery_Label_custName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(dmAddDelivery_custName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                        .addComponent(dmAddDelivery_date, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dmAddDelivery_time, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dmAddDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                        .addComponent(dmAddDelivery_Label_date)
                        .addGap(36, 36, 36)
                        .addComponent(dmAddDelivery_Label_time)
                        .addGap(73, 73, 73))))
        );

        dmAddDelivery_Panel_LocationInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Location Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmAddDelivery_Panel_LocationInfo.setName("dmAddDelivery_Panel_LocationInfo"); // NOI18N

        dmAddDelivery_Label_address.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_address.setText("Address");

        dmAddDelivery_address.setColumns(20);
        dmAddDelivery_address.setLineWrap(true);
        dmAddDelivery_address.setRows(5);
        dmAddDelivery_address.setWrapStyleWord(true);
        jScrollPane15.setViewportView(dmAddDelivery_address);

        dmAddDelivery_Label_town.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_town.setText("Town");

        dmAddDelivery_town.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddDelivery_townActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmAddDelivery_Panel_LocationInfoLayout = new javax.swing.GroupLayout(dmAddDelivery_Panel_LocationInfo);
        dmAddDelivery_Panel_LocationInfo.setLayout(dmAddDelivery_Panel_LocationInfoLayout);
        dmAddDelivery_Panel_LocationInfoLayout.setHorizontalGroup(
            dmAddDelivery_Panel_LocationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddDelivery_Panel_LocationInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmAddDelivery_Panel_LocationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dmAddDelivery_Label_town, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dmAddDelivery_Label_address, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(dmAddDelivery_Panel_LocationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addComponent(dmAddDelivery_town))
                .addGap(31, 31, 31))
        );
        dmAddDelivery_Panel_LocationInfoLayout.setVerticalGroup(
            dmAddDelivery_Panel_LocationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddDelivery_Panel_LocationInfoLayout.createSequentialGroup()
                .addGroup(dmAddDelivery_Panel_LocationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmAddDelivery_Panel_LocationInfoLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(dmAddDelivery_Label_address, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmAddDelivery_Panel_LocationInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(dmAddDelivery_Panel_LocationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dmAddDelivery_Label_town, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(dmAddDelivery_town))
                .addGap(24, 24, 24))
        );

        dmAddDelivery_Panel_VehicleInfp.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vehicle Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmAddDelivery_Panel_VehicleInfp.setName("dmAddDelivery_Panel_VehicleInfp"); // NOI18N

        dmAddDelivery_Label_vehicle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_vehicle.setText("Vehicle ID");

        dmAddDelivery_VehicleId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dmAddDelivery_VehicleId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        dmAddDelivery_VehicleId.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dmAddDelivery_VehicleIdItemStateChanged(evt);
            }
        });
        dmAddDelivery_VehicleId.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                dmAddDelivery_VehicleIdPopupMenuWillBecomeVisible(evt);
            }
        });

        dmAddDelivery_Label_type.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_type.setText("Type");

        dmAddDelivery_Label_driver.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_Label_driver.setText("Driver");

        dmAddDelivery_Driver.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        dmAddDelivery_Driver.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dmAddDelivery_DriverItemStateChanged(evt);
            }
        });
        dmAddDelivery_Driver.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                dmAddDelivery_DriverPopupMenuWillBecomeVisible(evt);
            }
        });

        dmAddDelivery_LabelName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_LabelName.setText("Name");

        dmAddDelivery_Name.setEditable(false);
        dmAddDelivery_Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddDelivery_NameActionPerformed(evt);
            }
        });

        dmAddDelivery_type.setEditable(false);
        dmAddDelivery_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddDelivery_typeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmAddDelivery_Panel_VehicleInfpLayout = new javax.swing.GroupLayout(dmAddDelivery_Panel_VehicleInfp);
        dmAddDelivery_Panel_VehicleInfp.setLayout(dmAddDelivery_Panel_VehicleInfpLayout);
        dmAddDelivery_Panel_VehicleInfpLayout.setHorizontalGroup(
            dmAddDelivery_Panel_VehicleInfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createSequentialGroup()
                        .addComponent(dmAddDelivery_LabelName, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dmAddDelivery_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createSequentialGroup()
                        .addComponent(dmAddDelivery_Label_driver, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(dmAddDelivery_Driver, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createSequentialGroup()
                        .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dmAddDelivery_Label_vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmAddDelivery_Label_type, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dmAddDelivery_type)
                            .addComponent(dmAddDelivery_VehicleId, 0, 292, Short.MAX_VALUE))))
                .addGap(22, 22, 22))
        );
        dmAddDelivery_Panel_VehicleInfpLayout.setVerticalGroup(
            dmAddDelivery_Panel_VehicleInfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmAddDelivery_Label_vehicle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dmAddDelivery_VehicleId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmAddDelivery_Label_type, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddDelivery_type, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmAddDelivery_Driver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddDelivery_Label_driver, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(dmAddDelivery_Panel_VehicleInfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmAddDelivery_LabelName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddDelivery_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        dmAddDelivery_btn_AddDelivery.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_btn_AddDelivery.setText("Add Delivery");
        dmAddDelivery_btn_AddDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddDelivery_btn_AddDeliveryActionPerformed(evt);
            }
        });

        dmAddDelivery_btnReset.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddDelivery_btnReset.setText("Reset");
        dmAddDelivery_btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddDelivery_btnResetActionPerformed(evt);
            }
        });

        jButton17.setText("Delivery Details");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmAddDelivery_Panel_AddDeliveryLayout = new javax.swing.GroupLayout(dmAddDelivery_Panel_AddDelivery);
        dmAddDelivery_Panel_AddDelivery.setLayout(dmAddDelivery_Panel_AddDeliveryLayout);
        dmAddDelivery_Panel_AddDeliveryLayout.setHorizontalGroup(
            dmAddDelivery_Panel_AddDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createSequentialGroup()
                .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createSequentialGroup()
                        .addGap(873, 873, 873)
                        .addComponent(jButton17))
                    .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createSequentialGroup()
                                .addComponent(dmAddDelivery_Panel_LocationInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(187, 187, 187)
                                .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dmAddDelivery_btn_AddDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dmAddDelivery_btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createSequentialGroup()
                                .addComponent(dmAddDelivery_Panel_DeliveryInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(95, 95, 95)
                                .addComponent(dmAddDelivery_Panel_VehicleInfp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createSequentialGroup()
                        .addGap(674, 674, 674)
                        .addComponent(dmAddDelivery_Label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(797, Short.MAX_VALUE))
        );
        dmAddDelivery_Panel_AddDeliveryLayout.setVerticalGroup(
            dmAddDelivery_Panel_AddDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(dmAddDelivery_Label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createSequentialGroup()
                        .addComponent(dmAddDelivery_Panel_VehicleInfp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(dmAddDelivery_btn_AddDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(dmAddDelivery_btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(270, 270, 270)
                        .addComponent(jButton17))
                    .addGroup(dmAddDelivery_Panel_AddDeliveryLayout.createSequentialGroup()
                        .addComponent(dmAddDelivery_Panel_DeliveryInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(dmAddDelivery_Panel_LocationInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6399, Short.MAX_VALUE))
        );

        jPanel3.add(dmAddDelivery_Panel_AddDelivery, "dmAddDelivery_Panel_AddDelivery");

        dmAddDelivery_Label_Title1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dmAddDelivery_Label_Title1.setText("Add Vehicle");

        dmAddVehicle_Panel_vehicleInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vehicle Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N

        dmAddVehicle_vehicleId.setEditable(false);
        dmAddVehicle_vehicleId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddVehicle_vehicleIdActionPerformed(evt);
            }
        });

        dmAddVehicle_Label_Resgister.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_Label_Resgister.setText("Registration Number ");

        dmAddVehicle_registerNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddVehicle_registerNumActionPerformed(evt);
            }
        });

        dmAddVehicle_Label_make.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_Label_make.setText("Make");

        dmAddVehicle_make.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dmAddVehicle_make.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select...", "Toyota", "Nissan", "Mazda", "Mitsubishi", "Bajaj", "Honda", "TVS" }));
        dmAddVehicle_make.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddVehicle_makeActionPerformed(evt);
            }
        });

        dmAddVehicle_Label_category.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_Label_category.setText("Category");

        dmAddVehicle_category.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dmAddVehicle_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select...", "Car", "Van", "Motor Bike", "Lorry" }));

        dmAddVehicle_Label_fuelType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_Label_fuelType.setText("Fuel Type");

        dmAddVehicle_fuelType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dmAddVehicle_fuelType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select...", "Petrol", "Diesel" }));

        dmAddVehicle_Label_desc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_Label_desc.setText("Description");

        dmAddVehicle_desc.setColumns(20);
        dmAddVehicle_desc.setLineWrap(true);
        dmAddVehicle_desc.setRows(5);
        dmAddVehicle_desc.setWrapStyleWord(true);
        jScrollPane16.setViewportView(dmAddVehicle_desc);

        dmAddVehicle_Label_date.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_Label_date.setText("Date");

        dmAddVehicle_Label_VehicleID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_Label_VehicleID.setText("Vehicle ID");

        dmAddVehicle_date.setEditable(false);
        dmAddVehicle_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddVehicle_dateActionPerformed(evt);
            }
        });

        dmAddVehicle_Label_capacity.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_Label_capacity.setText("Capacity");

        dmAddVehicle_capacity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddVehicle_capacityActionPerformed(evt);
            }
        });
        dmAddVehicle_capacity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dmAddVehicle_capacityKeyTyped(evt);
            }
        });

        dmAddVehicle_Label_kg.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmAddVehicle_Label_kg.setText("kg");

        javax.swing.GroupLayout dmAddVehicle_Panel_vehicleInfoLayout = new javax.swing.GroupLayout(dmAddVehicle_Panel_vehicleInfo);
        dmAddVehicle_Panel_vehicleInfo.setLayout(dmAddVehicle_Panel_vehicleInfoLayout);
        dmAddVehicle_Panel_vehicleInfoLayout.setHorizontalGroup(
            dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmAddVehicle_Label_make, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddVehicle_Label_category, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddVehicle_Label_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                        .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dmAddVehicle_Label_VehicleID, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmAddVehicle_Label_Resgister)
                            .addComponent(dmAddVehicle_Label_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmAddVehicle_Label_fuelType, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmAddVehicle_Label_date, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dmAddVehicle_make, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dmAddVehicle_registerNum)
                                    .addComponent(dmAddVehicle_vehicleId)
                                    .addComponent(dmAddVehicle_category, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                                    .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                                        .addComponent(dmAddVehicle_capacity)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dmAddVehicle_Label_kg, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                                    .addComponent(dmAddVehicle_fuelType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dmAddVehicle_date, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        dmAddVehicle_Panel_vehicleInfoLayout.setVerticalGroup(
            dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmAddVehicle_Label_VehicleID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(dmAddVehicle_vehicleId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmAddVehicle_registerNum, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddVehicle_Label_Resgister, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                        .addComponent(dmAddVehicle_Label_make, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                        .addComponent(dmAddVehicle_make, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createSequentialGroup()
                        .addComponent(dmAddVehicle_Label_category, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addComponent(dmAddVehicle_category, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmAddVehicle_Label_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddVehicle_Label_kg, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddVehicle_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmAddVehicle_Label_fuelType, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddVehicle_fuelType, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddVehicle_Label_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(dmAddVehicle_Panel_vehicleInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmAddVehicle_date, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmAddVehicle_Label_date, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dmAddVehicle_Panel_vehicleImg.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vehicle Image", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N

        dmAddVehicle_Label_img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dmAddVehicle_btnChooseImg.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmAddVehicle_btnChooseImg.setText("Choose Image...");
        dmAddVehicle_btnChooseImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddVehicle_btnChooseImgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmAddVehicle_Panel_vehicleImgLayout = new javax.swing.GroupLayout(dmAddVehicle_Panel_vehicleImg);
        dmAddVehicle_Panel_vehicleImg.setLayout(dmAddVehicle_Panel_vehicleImgLayout);
        dmAddVehicle_Panel_vehicleImgLayout.setHorizontalGroup(
            dmAddVehicle_Panel_vehicleImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddVehicle_Panel_vehicleImgLayout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addComponent(dmAddVehicle_Label_img, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addGroup(dmAddVehicle_Panel_vehicleImgLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(dmAddVehicle_btnChooseImg, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dmAddVehicle_Panel_vehicleImgLayout.setVerticalGroup(
            dmAddVehicle_Panel_vehicleImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddVehicle_Panel_vehicleImgLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(dmAddVehicle_Label_img, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(dmAddVehicle_btnChooseImg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        dmAddVehicle_btnAddVehicle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_btnAddVehicle.setText("Add Vehicle");
        dmAddVehicle_btnAddVehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddVehicle_btnAddVehicleActionPerformed(evt);
            }
        });

        dmAddVehicle_btnReset.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmAddVehicle_btnReset.setText("Reset");
        dmAddVehicle_btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmAddVehicle_btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmAddVehicle_Panel_AddVehicleLayout = new javax.swing.GroupLayout(dmAddVehicle_Panel_AddVehicle);
        dmAddVehicle_Panel_AddVehicle.setLayout(dmAddVehicle_Panel_AddVehicleLayout);
        dmAddVehicle_Panel_AddVehicleLayout.setHorizontalGroup(
            dmAddVehicle_Panel_AddVehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createSequentialGroup()
                .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(dmAddVehicle_Panel_vehicleInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dmAddVehicle_Panel_vehicleImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dmAddVehicle_btnAddVehicle, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                    .addComponent(dmAddVehicle_btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createSequentialGroup()
                        .addGap(754, 754, 754)
                        .addComponent(dmAddDelivery_Label_Title1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(651, 651, 651))
        );
        dmAddVehicle_Panel_AddVehicleLayout.setVerticalGroup(
            dmAddVehicle_Panel_AddVehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createSequentialGroup()
                .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(dmAddDelivery_Label_Title1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(dmAddVehicle_Panel_vehicleInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmAddVehicle_Panel_AddVehicleLayout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(dmAddVehicle_Panel_vehicleImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(dmAddVehicle_btnAddVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(dmAddVehicle_btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6602, Short.MAX_VALUE))
        );

        jPanel3.add(dmAddVehicle_Panel_AddVehicle, "dmAddVehicle_Panel_AddVehicle");

        dmDeliveryDetails_Panel_DeliveryDetails.setName("dmDeliveryDetails_Panel_DeliveryDetails"); // NOI18N

        dmDeliveryDetails_Label_Title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dmDeliveryDetails_Label_Title.setText("Delivery Details");

        dmDeliveryDetails_Search.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmDeliveryDetails_Search.setForeground(new java.awt.Color(204, 204, 204));
        dmDeliveryDetails_Search.setText("Search Here");
        dmDeliveryDetails_Search.setToolTipText("");
        dmDeliveryDetails_Search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dmDeliveryDetails_SearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dmDeliveryDetails_SearchFocusLost(evt);
            }
        });
        dmDeliveryDetails_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetails_SearchActionPerformed(evt);
            }
        });

        dmDeliveryDetails_Searchbtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmDeliveryDetails_Searchbtn.setText("Search");
        dmDeliveryDetails_Searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetails_SearchbtnActionPerformed(evt);
            }
        });

        dmDeliveryDetails_table.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        dmDeliveryDetails_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Delivery ID", "Location", "Date and Time", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dmDeliveryDetails_table.setOpaque(false);
        dmDeliveryDetails_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dmDeliveryDetails_tableMouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(dmDeliveryDetails_table);

        javax.swing.GroupLayout dmDeliveryDetails_Panel_DeliveryDetailsLayout = new javax.swing.GroupLayout(dmDeliveryDetails_Panel_DeliveryDetails);
        dmDeliveryDetails_Panel_DeliveryDetails.setLayout(dmDeliveryDetails_Panel_DeliveryDetailsLayout);
        dmDeliveryDetails_Panel_DeliveryDetailsLayout.setHorizontalGroup(
            dmDeliveryDetails_Panel_DeliveryDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dmDeliveryDetails_Panel_DeliveryDetailsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dmDeliveryDetails_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dmDeliveryDetails_Searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(877, 877, 877))
            .addGroup(dmDeliveryDetails_Panel_DeliveryDetailsLayout.createSequentialGroup()
                .addGroup(dmDeliveryDetails_Panel_DeliveryDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmDeliveryDetails_Panel_DeliveryDetailsLayout.createSequentialGroup()
                        .addGap(308, 308, 308)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 913, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmDeliveryDetails_Panel_DeliveryDetailsLayout.createSequentialGroup()
                        .addGap(662, 662, 662)
                        .addComponent(dmDeliveryDetails_Label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(854, Short.MAX_VALUE))
        );
        dmDeliveryDetails_Panel_DeliveryDetailsLayout.setVerticalGroup(
            dmDeliveryDetails_Panel_DeliveryDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmDeliveryDetails_Panel_DeliveryDetailsLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(dmDeliveryDetails_Label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(dmDeliveryDetails_Panel_DeliveryDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmDeliveryDetails_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetails_Searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6804, Short.MAX_VALUE))
        );

        jPanel3.add(dmDeliveryDetails_Panel_DeliveryDetails, "dmDeliveryDetails_Panel_DeliveryDetails");

        dmDeliveryDetailsDisc_Panel_DeliveryDetals.setName("dmDeliveryDetailsDisc_Panel_DeliveryDetals"); // NOI18N

        dmDeliveryDetailsDisc_Label_title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dmDeliveryDetailsDisc_Label_title.setText("Delivery Details");

        dmDeliveryDetailsDisc_Panel_DeliveryInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Delivery Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmDeliveryDetailsDisc_Panel_DeliveryInfo.setName("dmDeliveryDetailsDisc_Panel_DeliveryInfo"); // NOI18N

        dmDeliveryDetailsDisc_Label_deliveryID.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        dmDeliveryDetailsDisc_Label_deliveryID.setText("Delivery ID");

        dmDeliveryDetailsDisc_Label_billID.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        dmDeliveryDetailsDisc_Label_billID.setText("Bill ID");

        dmDeliveryDetailsDisc_Label_custName.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        dmDeliveryDetailsDisc_Label_custName.setText("Customer Name");

        dmDeliveryDetailsDisc_custName.setEditable(false);
        dmDeliveryDetailsDisc_custName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetailsDisc_custNameActionPerformed(evt);
            }
        });

        dmDeliveryDetailsDisc_Label_date.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        dmDeliveryDetailsDisc_Label_date.setText("Date");

        dmDeliveryDetailsDisc_date.setEditable(false);
        dmDeliveryDetailsDisc_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetailsDisc_dateActionPerformed(evt);
            }
        });

        dmDeliveryDetailsDisc_Label_time.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        dmDeliveryDetailsDisc_Label_time.setText("Time");

        dmDeliveryDetailsDisc_time.setEditable(false);
        dmDeliveryDetailsDisc_time.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetailsDisc_timeActionPerformed(evt);
            }
        });

        dmDeliveryDetailsDisc_billID.setEditable(false);
        dmDeliveryDetailsDisc_billID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetailsDisc_billIDActionPerformed(evt);
            }
        });

        dmDeliveryDetailsDisc_DeliveryID.setEditable(false);
        dmDeliveryDetailsDisc_DeliveryID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetailsDisc_DeliveryIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout = new javax.swing.GroupLayout(dmDeliveryDetailsDisc_Panel_DeliveryInfo);
        dmDeliveryDetailsDisc_Panel_DeliveryInfo.setLayout(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout);
        dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.setHorizontalGroup(
            dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmDeliveryDetailsDisc_Label_deliveryID, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_Label_billID, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_Label_custName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_Label_date, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_Label_time, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmDeliveryDetailsDisc_billID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_DeliveryID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_custName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_date, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_time, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );
        dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.setVerticalGroup(
            dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dmDeliveryDetailsDisc_Label_deliveryID, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_DeliveryID, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmDeliveryDetailsDisc_Label_billID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dmDeliveryDetailsDisc_billID, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmDeliveryDetailsDisc_Label_custName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_custName, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmDeliveryDetailsDisc_Label_date, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_date, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmDeliveryDetailsDisc_time, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_Label_time))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        dmDeliveryDetailsDisc_Panel_location.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Location Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmDeliveryDetailsDisc_Panel_location.setName("dmDeliveryDetailsDisc_Panel_location"); // NOI18N

        dmDeliveryDetailsDisc_Label_address.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmDeliveryDetailsDisc_Label_address.setText("Address");

        dmDeliveryDetails_address.setEditable(false);
        dmDeliveryDetails_address.setColumns(20);
        dmDeliveryDetails_address.setLineWrap(true);
        dmDeliveryDetails_address.setRows(5);
        dmDeliveryDetails_address.setWrapStyleWord(true);
        jScrollPane18.setViewportView(dmDeliveryDetails_address);

        dmDeliveryDetailsDisc_Label_town.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmDeliveryDetailsDisc_Label_town.setText("Town");

        dmDeliveryDetailsDisc_town.setEditable(false);
        dmDeliveryDetailsDisc_town.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetailsDisc_townActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmDeliveryDetailsDisc_Panel_locationLayout = new javax.swing.GroupLayout(dmDeliveryDetailsDisc_Panel_location);
        dmDeliveryDetailsDisc_Panel_location.setLayout(dmDeliveryDetailsDisc_Panel_locationLayout);
        dmDeliveryDetailsDisc_Panel_locationLayout.setHorizontalGroup(
            dmDeliveryDetailsDisc_Panel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmDeliveryDetailsDisc_Panel_locationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmDeliveryDetailsDisc_Panel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmDeliveryDetailsDisc_Label_address, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_Label_town, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(dmDeliveryDetailsDisc_Panel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(dmDeliveryDetailsDisc_town))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        dmDeliveryDetailsDisc_Panel_locationLayout.setVerticalGroup(
            dmDeliveryDetailsDisc_Panel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmDeliveryDetailsDisc_Panel_locationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmDeliveryDetailsDisc_Panel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmDeliveryDetailsDisc_Label_address, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(dmDeliveryDetailsDisc_Panel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmDeliveryDetailsDisc_town, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_Label_town, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        dmDeliveryDetailsDisc_Panel_vehicleINFO.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vehicle Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmDeliveryDetailsDisc_Panel_vehicleINFO.setName("dmDeliveryDetailsDisc_Panel_vehicleINFO"); // NOI18N

        dmDeliveryDetailsDisc_Label_vehicleID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmDeliveryDetailsDisc_Label_vehicleID.setText("Vehicle ID");

        dmDeliveryDetailsDisc_Label_type.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmDeliveryDetailsDisc_Label_type.setText("Type");

        dmDeliveryDetailsDisc_type.setEditable(false);
        dmDeliveryDetailsDisc_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetailsDisc_typeActionPerformed(evt);
            }
        });

        dmDeliveryDetails_vehicleID.setEditable(false);
        dmDeliveryDetails_vehicleID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetails_vehicleIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmDeliveryDetailsDisc_Panel_vehicleINFOLayout = new javax.swing.GroupLayout(dmDeliveryDetailsDisc_Panel_vehicleINFO);
        dmDeliveryDetailsDisc_Panel_vehicleINFO.setLayout(dmDeliveryDetailsDisc_Panel_vehicleINFOLayout);
        dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.setHorizontalGroup(
            dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmDeliveryDetailsDisc_Label_vehicleID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_Label_type, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dmDeliveryDetails_vehicleID, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(dmDeliveryDetailsDisc_type))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.setVerticalGroup(
            dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmDeliveryDetailsDisc_Label_vehicleID, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetails_vehicleID, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(dmDeliveryDetailsDisc_Panel_vehicleINFOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dmDeliveryDetailsDisc_Label_type, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmDeliveryDetailsDisc_type, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        dmDeliveryDetailsDisc_btn_delete.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmDeliveryDetailsDisc_btn_delete.setText("Delete");
        dmDeliveryDetailsDisc_btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetailsDisc_btn_deleteActionPerformed(evt);
            }
        });

        dmDeliveryDetailsDisc_btn_Update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmDeliveryDetailsDisc_btn_Update.setText("Update");
        dmDeliveryDetailsDisc_btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmDeliveryDetailsDisc_btn_UpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout = new javax.swing.GroupLayout(dmDeliveryDetailsDisc_Panel_DeliveryDetals);
        dmDeliveryDetailsDisc_Panel_DeliveryDetals.setLayout(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout);
        dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.setHorizontalGroup(
            dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createSequentialGroup()
                .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dmDeliveryDetailsDisc_Panel_vehicleINFO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createSequentialGroup()
                        .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createSequentialGroup()
                                .addGap(144, 144, 144)
                                .addComponent(dmDeliveryDetailsDisc_Panel_DeliveryInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createSequentialGroup()
                                .addGap(286, 286, 286)
                                .addComponent(dmDeliveryDetailsDisc_btn_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dmDeliveryDetailsDisc_btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dmDeliveryDetailsDisc_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(dmDeliveryDetailsDisc_Panel_location, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(709, Short.MAX_VALUE))
        );
        dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.setVerticalGroup(
            dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(dmDeliveryDetailsDisc_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createSequentialGroup()
                        .addComponent(dmDeliveryDetailsDisc_Panel_DeliveryInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dmDeliveryDetailsDisc_btn_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmDeliveryDetailsDisc_btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dmDeliveryDetailsDisc_Panel_DeliveryDetalsLayout.createSequentialGroup()
                        .addComponent(dmDeliveryDetailsDisc_Panel_location, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(dmDeliveryDetailsDisc_Panel_vehicleINFO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6663, Short.MAX_VALUE))
        );

        jPanel3.add(dmDeliveryDetailsDisc_Panel_DeliveryDetals, "dmDeliveryDetailsDisc_Panel_DeliveryDetals");

        dmupdateDelivery_Panel_UpdateDelivery.setName("dmupdateDelivery_Panel_UpdateDelivery"); // NOI18N

        dmupdateDelivery_Label_title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dmupdateDelivery_Label_title.setText("Update Delivery");

        dmupdateDelivery_Panel_DeliveryInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Delivery Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmupdateDelivery_Panel_DeliveryInfo.setName("dmupdateDelivery_Panel_DeliveryInfo"); // NOI18N

        dmupdateDelivery_Label_deliveryID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_Label_deliveryID.setText("Delivery ID");

        dmupdateDelivery_Label_BillID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_Label_BillID.setText("Bill ID");

        dmupdateDelivery_Label_custName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_Label_custName.setText("Customer Name");

        dmupdateDelivery_custName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmupdateDelivery_custNameActionPerformed(evt);
            }
        });

        dmupdateDelivery_Label_date.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_Label_date.setText("Date");

        dmupdateDelivery_Date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmupdateDelivery_DateActionPerformed(evt);
            }
        });

        dmupdateDelivery_Label_time.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_Label_time.setText("Time");

        dmupdateDelivery_time.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmupdateDelivery_timeActionPerformed(evt);
            }
        });

        dmupdateDelivery_billID.setEditable(false);
        dmupdateDelivery_billID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmupdateDelivery_billIDActionPerformed(evt);
            }
        });

        dmupdateDelivery_DeliveryID.setEditable(false);
        dmupdateDelivery_DeliveryID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmupdateDelivery_DeliveryIDActionPerformed(evt);
            }
        });

        dmupdateDelivery_Label_am.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmupdateDelivery_Label_am.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "am", "pm" }));
        dmupdateDelivery_Label_am.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                dmupdateDelivery_Label_amPopupMenuWillBecomeVisible(evt);
            }
        });

        javax.swing.GroupLayout dmupdateDelivery_Panel_DeliveryInfoLayout = new javax.swing.GroupLayout(dmupdateDelivery_Panel_DeliveryInfo);
        dmupdateDelivery_Panel_DeliveryInfo.setLayout(dmupdateDelivery_Panel_DeliveryInfoLayout);
        dmupdateDelivery_Panel_DeliveryInfoLayout.setHorizontalGroup(
            dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                        .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dmupdateDelivery_Label_custName, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmupdateDelivery_Label_date, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmupdateDelivery_Label_time, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dmupdateDelivery_Date)
                            .addComponent(dmupdateDelivery_custName)
                            .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                                .addComponent(dmupdateDelivery_time, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dmupdateDelivery_Label_am, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                        .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dmupdateDelivery_Label_deliveryID, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                            .addComponent(dmupdateDelivery_Label_BillID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(90, 90, 90)
                        .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dmupdateDelivery_billID)
                            .addComponent(dmupdateDelivery_DeliveryID))))
                .addGap(57, 57, 57))
        );
        dmupdateDelivery_Panel_DeliveryInfoLayout.setVerticalGroup(
            dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmupdateDelivery_Label_deliveryID, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmupdateDelivery_DeliveryID, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmupdateDelivery_Label_BillID, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmupdateDelivery_billID, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmupdateDelivery_Label_custName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmupdateDelivery_custName, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmupdateDelivery_Label_date, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmupdateDelivery_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(dmupdateDelivery_Panel_DeliveryInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmupdateDelivery_time, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmupdateDelivery_Label_time, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmupdateDelivery_Label_am, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        dmupdateDelivery_Panel_Location.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Location Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmupdateDelivery_Panel_Location.setName("dmupdateDelivery_Panel_Location"); // NOI18N

        dmupdateDelivery_Label_address.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_Label_address.setText("Address");

        dmupdateDelivery_address.setColumns(20);
        dmupdateDelivery_address.setRows(5);
        jScrollPane19.setViewportView(dmupdateDelivery_address);

        dmupdateDelivery_Label_town.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_Label_town.setText("Town");

        dmupdateDelivery_town.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmupdateDelivery_townActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmupdateDelivery_Panel_LocationLayout = new javax.swing.GroupLayout(dmupdateDelivery_Panel_Location);
        dmupdateDelivery_Panel_Location.setLayout(dmupdateDelivery_Panel_LocationLayout);
        dmupdateDelivery_Panel_LocationLayout.setHorizontalGroup(
            dmupdateDelivery_Panel_LocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmupdateDelivery_Panel_LocationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmupdateDelivery_Panel_LocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmupdateDelivery_Label_address, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmupdateDelivery_Label_town, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(dmupdateDelivery_Panel_LocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(dmupdateDelivery_town))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        dmupdateDelivery_Panel_LocationLayout.setVerticalGroup(
            dmupdateDelivery_Panel_LocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmupdateDelivery_Panel_LocationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmupdateDelivery_Panel_LocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmupdateDelivery_Label_address, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(dmupdateDelivery_Panel_LocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmupdateDelivery_town, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmupdateDelivery_Label_town, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        dmupdateDelivery_Panel_Vehicle.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vehicle Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmupdateDelivery_Panel_Vehicle.setName("dmupdateDelivery_Panel_Vehicle"); // NOI18N

        dmupdateDelivery_Label_VehicleID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_Label_VehicleID.setText("Vehicle ID");

        dmupdateDelivery_vehicleID.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmupdateDelivery_vehicleID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        dmupdateDelivery_vehicleID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                dmupdateDelivery_vehicleIDPopupMenuWillBecomeVisible(evt);
            }
        });

        dmupdateDelivery_Label_type.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_Label_type.setText("Type");

        dmupdateDelivery_type.setEditable(false);
        dmupdateDelivery_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmupdateDelivery_typeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmupdateDelivery_Panel_VehicleLayout = new javax.swing.GroupLayout(dmupdateDelivery_Panel_Vehicle);
        dmupdateDelivery_Panel_Vehicle.setLayout(dmupdateDelivery_Panel_VehicleLayout);
        dmupdateDelivery_Panel_VehicleLayout.setHorizontalGroup(
            dmupdateDelivery_Panel_VehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmupdateDelivery_Panel_VehicleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmupdateDelivery_Panel_VehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmupdateDelivery_Label_VehicleID, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dmupdateDelivery_Panel_VehicleLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(dmupdateDelivery_Label_type, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(dmupdateDelivery_Panel_VehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dmupdateDelivery_vehicleID, 0, 237, Short.MAX_VALUE)
                    .addComponent(dmupdateDelivery_type))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        dmupdateDelivery_Panel_VehicleLayout.setVerticalGroup(
            dmupdateDelivery_Panel_VehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmupdateDelivery_Panel_VehicleLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(dmupdateDelivery_Panel_VehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dmupdateDelivery_Label_VehicleID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dmupdateDelivery_vehicleID, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(dmupdateDelivery_Panel_VehicleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dmupdateDelivery_Label_type, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(dmupdateDelivery_type))
                .addContainerGap())
        );

        dmupdateDelivery_btn_Update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmupdateDelivery_btn_Update.setText("Update Delivery");
        dmupdateDelivery_btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmupdateDelivery_btn_UpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmupdateDelivery_Panel_UpdateDeliveryLayout = new javax.swing.GroupLayout(dmupdateDelivery_Panel_UpdateDelivery);
        dmupdateDelivery_Panel_UpdateDelivery.setLayout(dmupdateDelivery_Panel_UpdateDeliveryLayout);
        dmupdateDelivery_Panel_UpdateDeliveryLayout.setHorizontalGroup(
            dmupdateDelivery_Panel_UpdateDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmupdateDelivery_Panel_UpdateDeliveryLayout.createSequentialGroup()
                .addGroup(dmupdateDelivery_Panel_UpdateDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmupdateDelivery_Panel_UpdateDeliveryLayout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(dmupdateDelivery_Panel_DeliveryInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(164, 164, 164)
                        .addGroup(dmupdateDelivery_Panel_UpdateDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dmupdateDelivery_Panel_Location, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmupdateDelivery_Panel_Vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dmupdateDelivery_Panel_UpdateDeliveryLayout.createSequentialGroup()
                        .addGap(800, 800, 800)
                        .addComponent(dmupdateDelivery_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmupdateDelivery_Panel_UpdateDeliveryLayout.createSequentialGroup()
                        .addGap(498, 498, 498)
                        .addComponent(dmupdateDelivery_btn_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(664, Short.MAX_VALUE))
        );
        dmupdateDelivery_Panel_UpdateDeliveryLayout.setVerticalGroup(
            dmupdateDelivery_Panel_UpdateDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmupdateDelivery_Panel_UpdateDeliveryLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(dmupdateDelivery_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addGroup(dmupdateDelivery_Panel_UpdateDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmupdateDelivery_Panel_DeliveryInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dmupdateDelivery_Panel_UpdateDeliveryLayout.createSequentialGroup()
                        .addComponent(dmupdateDelivery_Panel_Location, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(dmupdateDelivery_Panel_Vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(dmupdateDelivery_btn_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6605, Short.MAX_VALUE))
        );

        jPanel3.add(dmupdateDelivery_Panel_UpdateDelivery, "dmupdateDelivery_Panel_UpdateDelivery");

        dmVehicleDetails_Panel_main.setName("dmVehicleDetails_Panel_main"); // NOI18N

        dmVehicleDetails_Label_title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dmVehicleDetails_Label_title.setText("Vehicle Details");

        dmVehicleDetails_search.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmVehicleDetails_search.setForeground(new java.awt.Color(204, 204, 204));
        dmVehicleDetails_search.setText("Search Here");
        dmVehicleDetails_search.setToolTipText("");
        dmVehicleDetails_search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dmVehicleDetails_searchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dmVehicleDetails_searchFocusLost(evt);
            }
        });
        dmVehicleDetails_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetails_searchActionPerformed(evt);
            }
        });

        dmVehicleDetails_btn.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmVehicleDetails_btn.setText("Search");
        dmVehicleDetails_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetails_btnActionPerformed(evt);
            }
        });

        dmVehicleDetails_table.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        dmVehicleDetails_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vehicle Number", "Capacity", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dmVehicleDetails_table.getTableHeader().setReorderingAllowed(false);
        dmVehicleDetails_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dmVehicleDetails_tableMouseClicked(evt);
            }
        });
        dmVehicleDetails_scroll.setViewportView(dmVehicleDetails_table);

        javax.swing.GroupLayout dmVehicleDetails_Panel_mainLayout = new javax.swing.GroupLayout(dmVehicleDetails_Panel_main);
        dmVehicleDetails_Panel_main.setLayout(dmVehicleDetails_Panel_mainLayout);
        dmVehicleDetails_Panel_mainLayout.setHorizontalGroup(
            dmVehicleDetails_Panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmVehicleDetails_Panel_mainLayout.createSequentialGroup()
                .addGroup(dmVehicleDetails_Panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmVehicleDetails_Panel_mainLayout.createSequentialGroup()
                        .addGap(613, 613, 613)
                        .addComponent(dmVehicleDetails_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmVehicleDetails_Panel_mainLayout.createSequentialGroup()
                        .addGap(865, 865, 865)
                        .addComponent(dmVehicleDetails_search, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dmVehicleDetails_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmVehicleDetails_Panel_mainLayout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(dmVehicleDetails_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(914, Short.MAX_VALUE))
        );
        dmVehicleDetails_Panel_mainLayout.setVerticalGroup(
            dmVehicleDetails_Panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmVehicleDetails_Panel_mainLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(dmVehicleDetails_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(dmVehicleDetails_Panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmVehicleDetails_search, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmVehicleDetails_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addComponent(dmVehicleDetails_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6747, Short.MAX_VALUE))
        );

        jPanel3.add(dmVehicleDetails_Panel_main, "dmVehicleDetails_Panel_main");

        dmVehicleDetailsDisc_Panel_Main.setName("dmVehicleDetailsDisc_Panel_Main"); // NOI18N

        dmVehicleDetailsDisc_Label_title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dmVehicleDetailsDisc_Label_title.setText("Vehicle Details");

        dmVehicleDetailsDisc_Label_id.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_Label_id.setText("Vehicle ID");

        dmVehicleDetailsDisc_id.setEditable(false);
        dmVehicleDetailsDisc_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetailsDisc_idActionPerformed(evt);
            }
        });

        dmVehicleDetailsDisc_Label_num.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_Label_num.setText("Registration Number");

        dmVehicleDetailsDisc_register.setEditable(false);
        dmVehicleDetailsDisc_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetailsDisc_registerActionPerformed(evt);
            }
        });

        dmVehicleDetailsDisc_Label_cat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_Label_cat.setText("Category");

        dmVehicleDetailsDisc_Label_make.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_Label_make.setText("Make");

        dmVehicleDetailsDisc_Label_fuel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_Label_fuel.setText("Fuel Type");

        dmVehicleDetailsDisc_Label_capa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_Label_capa.setText("Capacity");

        dmVehicleDetailsDisc_Label_desc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_Label_desc.setText("Description");

        dmVehicleDetailsDisc_make.setEditable(false);
        dmVehicleDetailsDisc_make.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetailsDisc_makeActionPerformed(evt);
            }
        });

        dmVehicleDetailsDisc_cate.setEditable(false);
        dmVehicleDetailsDisc_cate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetailsDisc_cateActionPerformed(evt);
            }
        });

        dmVehicleDetailsDisc_fuel.setEditable(false);
        dmVehicleDetailsDisc_fuel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetailsDisc_fuelActionPerformed(evt);
            }
        });

        dmVehicleDetailsDisc_capa.setEditable(false);
        dmVehicleDetailsDisc_capa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetailsDisc_capaActionPerformed(evt);
            }
        });

        dmVehicleDetailsDisc_desc.setEditable(false);
        dmVehicleDetailsDisc_desc.setColumns(20);
        dmVehicleDetailsDisc_desc.setLineWrap(true);
        dmVehicleDetailsDisc_desc.setRows(5);
        dmVehicleDetailsDisc_desc.setWrapStyleWord(true);
        jScrollPane20.setViewportView(dmVehicleDetailsDisc_desc);

        dmVehicleDetailsDisc_Labeldate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_Labeldate.setText("Added Date");

        dmVehicleDetailsDisc_date.setEditable(false);
        dmVehicleDetailsDisc_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetailsDisc_dateActionPerformed(evt);
            }
        });

        dmVehicleDetailsDisc_Label_kg.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        dmVehicleDetailsDisc_Label_kg.setText("kg");

        dmVehicleDetailsDisc_btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_btnUpdate.setText("Update");
        dmVehicleDetailsDisc_btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetailsDisc_btnUpdateActionPerformed(evt);
            }
        });

        dmVehicleDetailsDisc_btnDelete.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmVehicleDetailsDisc_btnDelete.setText("Delete");
        dmVehicleDetailsDisc_btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmVehicleDetailsDisc_btnDeleteActionPerformed(evt);
            }
        });

        dmVehicleDetailsDisc_Label_img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout dmVehicleDetailsDisc_Panel_MainLayout = new javax.swing.GroupLayout(dmVehicleDetailsDisc_Panel_Main);
        dmVehicleDetailsDisc_Panel_Main.setLayout(dmVehicleDetailsDisc_Panel_MainLayout);
        dmVehicleDetailsDisc_Panel_MainLayout.setHorizontalGroup(
            dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dmVehicleDetailsDisc_Label_num, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dmVehicleDetailsDisc_Label_cat, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dmVehicleDetailsDisc_Label_fuel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dmVehicleDetailsDisc_Label_capa, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dmVehicleDetailsDisc_Label_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(dmVehicleDetailsDisc_Labeldate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                        .addComponent(dmVehicleDetailsDisc_Label_make, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(74, 74, 74)
                                .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dmVehicleDetailsDisc_register)
                                    .addComponent(dmVehicleDetailsDisc_make)
                                    .addComponent(dmVehicleDetailsDisc_cate)
                                    .addComponent(dmVehicleDetailsDisc_fuel)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                        .addComponent(dmVehicleDetailsDisc_capa)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dmVehicleDetailsDisc_Label_kg, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                                    .addComponent(dmVehicleDetailsDisc_date)))
                            .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addComponent(dmVehicleDetailsDisc_Label_id, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(136, 136, 136)
                                .addComponent(dmVehicleDetailsDisc_id, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(dmVehicleDetailsDisc_Label_img, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addGap(173, 173, 173)
                                .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dmVehicleDetailsDisc_btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                                    .addComponent(dmVehicleDetailsDisc_btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                        .addGap(641, 641, 641)
                        .addComponent(dmVehicleDetailsDisc_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(930, Short.MAX_VALUE))
        );
        dmVehicleDetailsDisc_Panel_MainLayout.setVerticalGroup(
            dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(dmVehicleDetailsDisc_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dmVehicleDetailsDisc_Label_id, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmVehicleDetailsDisc_id, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dmVehicleDetailsDisc_Label_num, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmVehicleDetailsDisc_register, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dmVehicleDetailsDisc_Label_make, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmVehicleDetailsDisc_make, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(dmVehicleDetailsDisc_Label_cat, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dmVehicleDetailsDisc_cate, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(dmVehicleDetailsDisc_Label_img, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dmVehicleDetailsDisc_fuel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmVehicleDetailsDisc_Label_fuel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addComponent(dmVehicleDetailsDisc_Label_capa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dmVehicleDetailsDisc_capa, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dmVehicleDetailsDisc_Label_kg, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)))
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addComponent(dmVehicleDetailsDisc_Label_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dmVehicleDetailsDisc_Labeldate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmVehicleDetailsDisc_date, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dmVehicleDetailsDisc_Panel_MainLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(dmVehicleDetailsDisc_btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(dmVehicleDetailsDisc_btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6607, Short.MAX_VALUE))
        );

        jPanel3.add(dmVehicleDetailsDisc_Panel_Main, "dmVehicleDetailsDisc_Panel_Main");

        dmUpdateVehicle_Panel_main.setName("dmUpdateVehicle_Panel_main"); // NOI18N

        dmUpdateVehicle_Label_title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dmUpdateVehicle_Label_title.setText("Update Vehicle Information");

        dmUpdateVehicle_Panel_info.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vehicle Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20))); // NOI18N
        dmUpdateVehicle_Panel_info.setName("dmUpdateVehicle_Panel_info"); // NOI18N

        dmUpdateVehicle_vehicleId.setEditable(false);
        dmUpdateVehicle_vehicleId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmUpdateVehicle_vehicleIdActionPerformed(evt);
            }
        });

        dmUpdateVehicle_Label_num.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmUpdateVehicle_Label_num.setText("Registration Number ");

        dmUpdateVehicle_registerNum.setEditable(false);
        dmUpdateVehicle_registerNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmUpdateVehicle_registerNumActionPerformed(evt);
            }
        });

        dmUpdateVehicle_Label_make.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmUpdateVehicle_Label_make.setText("Make");

        dmUpdateVehicle_make.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmUpdateVehicle_make.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select...", "Toyota", "Nissan", "Mazda", "Mitsubishi", "Bajaj", "Honda", "TVS" }));
        dmUpdateVehicle_make.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmUpdateVehicle_makeActionPerformed(evt);
            }
        });

        dmUpdateVehicle_Label_cate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmUpdateVehicle_Label_cate.setText("Category");

        dmUpdateVehicle_category.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmUpdateVehicle_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select...", "Car", "Van", "Motor Bike", "Lorry" }));

        dmUpdateVehicle_Label_fuel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmUpdateVehicle_Label_fuel.setText("Fuel Type");

        dmUpdateVehicle_fuelType.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmUpdateVehicle_fuelType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select...", "Petrol", "Diesel" }));

        dmUpdateVehicle_Label_desc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmUpdateVehicle_Label_desc.setText("Description");

        dmUpdateVehicle_desc.setColumns(20);
        dmUpdateVehicle_desc.setLineWrap(true);
        dmUpdateVehicle_desc.setRows(5);
        dmUpdateVehicle_desc.setWrapStyleWord(true);
        jScrollPane21.setViewportView(dmUpdateVehicle_desc);

        datedmUpdateVehicle_Label_date.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        datedmUpdateVehicle_Label_date.setText("Date");

        dmUpdateVehicle_Label_id.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmUpdateVehicle_Label_id.setText("Vehicle ID");

        dmUpdateVehicle_date.setEditable(false);
        dmUpdateVehicle_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmUpdateVehicle_dateActionPerformed(evt);
            }
        });

        dmUpdateVehicle_Label_capa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmUpdateVehicle_Label_capa.setText("Capacity");

        dmUpdateVehicle_capacity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmUpdateVehicle_capacityActionPerformed(evt);
            }
        });

        dmUpdateVehicle_kg.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        dmUpdateVehicle_kg.setText("kg");

        javax.swing.GroupLayout dmUpdateVehicle_Panel_infoLayout = new javax.swing.GroupLayout(dmUpdateVehicle_Panel_info);
        dmUpdateVehicle_Panel_info.setLayout(dmUpdateVehicle_Panel_infoLayout);
        dmUpdateVehicle_Panel_infoLayout.setHorizontalGroup(
            dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dmUpdateVehicle_Label_id, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dmUpdateVehicle_Label_num))
                    .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dmUpdateVehicle_Label_cate, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dmUpdateVehicle_Label_make, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dmUpdateVehicle_Label_fuel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dmUpdateVehicle_Label_capa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dmUpdateVehicle_Label_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(datedmUpdateVehicle_Label_date, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmUpdateVehicle_date, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                    .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                            .addComponent(dmUpdateVehicle_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(dmUpdateVehicle_kg, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane21))
                    .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(dmUpdateVehicle_fuelType, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dmUpdateVehicle_registerNum, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dmUpdateVehicle_vehicleId, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dmUpdateVehicle_make, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dmUpdateVehicle_category, javax.swing.GroupLayout.Alignment.LEADING, 0, 321, Short.MAX_VALUE)))
                .addGap(51, 51, 51))
        );
        dmUpdateVehicle_Panel_infoLayout.setVerticalGroup(
            dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmUpdateVehicle_Panel_infoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dmUpdateVehicle_Label_id, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmUpdateVehicle_vehicleId, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmUpdateVehicle_Label_num, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmUpdateVehicle_registerNum, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmUpdateVehicle_Label_make, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmUpdateVehicle_make, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmUpdateVehicle_Label_cate, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmUpdateVehicle_category, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmUpdateVehicle_fuelType, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmUpdateVehicle_Label_fuel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmUpdateVehicle_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmUpdateVehicle_Label_capa, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmUpdateVehicle_kg, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dmUpdateVehicle_Label_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dmUpdateVehicle_Panel_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datedmUpdateVehicle_Label_date, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmUpdateVehicle_date, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 24, Short.MAX_VALUE))
        );

        dmUpdateVehicle_Panel_img.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vehicle Image", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        dmUpdateVehicle_Panel_img.setName("dmUpdateVehicle_Panel_img"); // NOI18N

        dmUpdateVehicle_Label_img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dmUpdateVehicle_btnChoose_.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        dmUpdateVehicle_btnChoose_.setText("Change Image");
        dmUpdateVehicle_btnChoose_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmUpdateVehicle_btnChoose_ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmUpdateVehicle_Panel_imgLayout = new javax.swing.GroupLayout(dmUpdateVehicle_Panel_img);
        dmUpdateVehicle_Panel_img.setLayout(dmUpdateVehicle_Panel_imgLayout);
        dmUpdateVehicle_Panel_imgLayout.setHorizontalGroup(
            dmUpdateVehicle_Panel_imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmUpdateVehicle_Panel_imgLayout.createSequentialGroup()
                .addGroup(dmUpdateVehicle_Panel_imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmUpdateVehicle_Panel_imgLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(dmUpdateVehicle_Label_img, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dmUpdateVehicle_Panel_imgLayout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(dmUpdateVehicle_btnChoose_, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        dmUpdateVehicle_Panel_imgLayout.setVerticalGroup(
            dmUpdateVehicle_Panel_imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmUpdateVehicle_Panel_imgLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(dmUpdateVehicle_Label_img, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(dmUpdateVehicle_btnChoose_, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        dmUpdateVehicle_btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dmUpdateVehicle_btnUpdate.setText("Update Vehicle");
        dmUpdateVehicle_btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmUpdateVehicle_btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dmUpdateVehicle_Panel_mainLayout = new javax.swing.GroupLayout(dmUpdateVehicle_Panel_main);
        dmUpdateVehicle_Panel_main.setLayout(dmUpdateVehicle_Panel_mainLayout);
        dmUpdateVehicle_Panel_mainLayout.setHorizontalGroup(
            dmUpdateVehicle_Panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmUpdateVehicle_Panel_mainLayout.createSequentialGroup()
                .addGroup(dmUpdateVehicle_Panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmUpdateVehicle_Panel_mainLayout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(dmUpdateVehicle_Panel_info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(dmUpdateVehicle_Panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dmUpdateVehicle_Panel_mainLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(dmUpdateVehicle_Panel_img, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dmUpdateVehicle_Panel_mainLayout.createSequentialGroup()
                                .addGap(202, 202, 202)
                                .addComponent(dmUpdateVehicle_btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(dmUpdateVehicle_Panel_mainLayout.createSequentialGroup()
                        .addGap(628, 628, 628)
                        .addComponent(dmUpdateVehicle_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(868, Short.MAX_VALUE))
        );
        dmUpdateVehicle_Panel_mainLayout.setVerticalGroup(
            dmUpdateVehicle_Panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dmUpdateVehicle_Panel_mainLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(dmUpdateVehicle_Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(dmUpdateVehicle_Panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dmUpdateVehicle_Panel_mainLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(dmUpdateVehicle_Panel_img, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(dmUpdateVehicle_btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dmUpdateVehicle_Panel_mainLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(dmUpdateVehicle_Panel_info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(6579, Short.MAX_VALUE))))
        );

        jPanel3.add(dmUpdateVehicle_Panel_main, "dmUpdateVehicle_Panel_main");

        supOrderDetails.setBackground(new java.awt.Color(153, 153, 153));
        supOrderDetails.setPreferredSize(new java.awt.Dimension(2291, 974));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Item ID", "Order Qty", "Net Amount", "Supplier ID", "Date ", "Approved", "Rejected", "Received"
            }
        ));
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        Ordrtble.setViewportView(orderTable);

        jPanel5.setBackground(new java.awt.Color(77, 78, 80));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        orderIDtxt.setEditable(false);
        orderIDtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderIDtxtActionPerformed(evt);
            }
        });

        Deletebtnorder.setBackground(new java.awt.Color(160, 31, 38));
        Deletebtnorder.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Deletebtnorder.setForeground(new java.awt.Color(255, 255, 255));
        Deletebtnorder.setText("DELETE");
        Deletebtnorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeletebtnorderActionPerformed(evt);
            }
        });

        suppIdlabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        suppIdlabel.setForeground(new java.awt.Color(255, 255, 255));
        suppIdlabel.setText("SUPPLIER ID          :");

        qtyLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        qtyLabel.setForeground(new java.awt.Color(255, 255, 255));
        qtyLabel.setText("ORDER QUANTITY   :");

        itemIdLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        itemIdLabel.setForeground(new java.awt.Color(255, 255, 255));
        itemIdLabel.setText(" ITEM ID                :");

        supIDcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supIDcomboActionPerformed(evt);
            }
        });

        Itemcombo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ItemcomboMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ItemcomboMouseExited(evt);
            }
        });

        OrderIdlabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        OrderIdlabel.setForeground(new java.awt.Color(255, 255, 255));
        OrderIdlabel.setText("ORDER ID              :");

        amountLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        amountLabel.setForeground(new java.awt.Color(255, 255, 255));
        amountLabel.setText("NET AMOUNT         : ");

        placeOrder.setBackground(new java.awt.Color(0, 102, 51));
        placeOrder.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        placeOrder.setForeground(new java.awt.Color(255, 255, 255));
        placeOrder.setText("PLACE ORDER");
        placeOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(suppIdlabel)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(amountLabel)
                                    .addComponent(placeOrder))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(quantitytxt, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Deletebtnorder, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(supIDcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(amounttxt, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(OrderIdlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(qtyLabel)
                                    .addComponent(itemIdLabel))))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Itemcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(orderIDtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(442, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OrderIdlabel)
                    .addComponent(orderIDtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(itemIdLabel)
                    .addComponent(Itemcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qtyLabel)
                    .addComponent(quantitytxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(suppIdlabel)
                    .addComponent(supIDcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amountLabel)
                    .addComponent(amounttxt, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(placeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Deletebtnorder, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96))
        );

        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);

        orderjLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        orderjLabel9.setForeground(new java.awt.Color(255, 255, 255));
        orderjLabel9.setText("SEARCH BY");

        orderjComboBox3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        orderjComboBox3.setForeground(new java.awt.Color(255, 255, 255));
        orderjComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "orderid", "itemId", "supId" }));

        OrderjTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderjTextField5ActionPerformed(evt);
            }
        });
        OrderjTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                OrderjTextField5KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout supOrderDetailsLayout = new javax.swing.GroupLayout(supOrderDetails);
        supOrderDetails.setLayout(supOrderDetailsLayout);
        supOrderDetailsLayout.setHorizontalGroup(
            supOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supOrderDetailsLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(supOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(supOrderDetailsLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(supOrderDetailsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Ordrtble, javax.swing.GroupLayout.PREFERRED_SIZE, 751, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(supOrderDetailsLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(orderjLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(orderjComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(OrderjTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(437, Short.MAX_VALUE))
        );
        supOrderDetailsLayout.setVerticalGroup(
            supOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supOrderDetailsLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addGroup(supOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(supOrderDetailsLayout.createSequentialGroup()
                        .addGroup(supOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(supOrderDetailsLayout.createSequentialGroup()
                                .addComponent(orderjLabel9)
                                .addGap(21, 21, 21))
                            .addGroup(supOrderDetailsLayout.createSequentialGroup()
                                .addGroup(supOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(orderjComboBox3)
                                    .addComponent(OrderjTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(Ordrtble, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 763, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(supOrderDetails, "supOrderDetails");

        supAddDetails.setBackground(new java.awt.Color(153, 153, 153));

        supName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supNameActionPerformed(evt);
            }
        });

        supID.setEditable(false);

        supUpdatebtn.setBackground(new java.awt.Color(28, 47, 123));
        supUpdatebtn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        supUpdatebtn.setForeground(new java.awt.Color(255, 255, 255));
        supUpdatebtn.setText("UPDATE");
        supUpdatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supUpdatebtnActionPerformed(evt);
            }
        });

        supAddbtn.setBackground(new java.awt.Color(0, 102, 51));
        supAddbtn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        supAddbtn.setForeground(new java.awt.Color(255, 255, 255));
        supAddbtn.setText("ADD");
        supAddbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supAddbtnActionPerformed(evt);
            }
        });

        supNolabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        supNolabel.setForeground(new java.awt.Color(255, 255, 255));
        supNolabel.setText("PHONE NUMBER :");

        supEmaillabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        supEmaillabel.setForeground(new java.awt.Color(255, 255, 255));
        supEmaillabel.setText("E-MAIL              :");

        supNamelabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        supNamelabel.setForeground(new java.awt.Color(255, 255, 255));
        supNamelabel.setText("NAME                :");

        supIdlabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        supIdlabel.setForeground(new java.awt.Color(255, 255, 255));
        supIdlabel.setText("SUPPLIER ID       :");

        supAddresslabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        supAddresslabel.setForeground(new java.awt.Color(255, 255, 255));
        supAddresslabel.setText("ADDRESS          :");

        supAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supAddressActionPerformed(evt);
            }
        });

        supTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "NAME", "E-MAIL", "PHONE NO", "ADDRESS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        supTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                supTableMouseClicked(evt);
            }
        });
        supTble.setViewportView(supTable);

        supDeletebtn.setBackground(new java.awt.Color(160, 31, 38));
        supDeletebtn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        supDeletebtn.setForeground(new java.awt.Color(255, 255, 255));
        supDeletebtn.setText("DELETE");
        supDeletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supDeletebtnActionPerformed(evt);
            }
        });

        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        supjLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        supjLabel9.setForeground(new java.awt.Color(255, 255, 255));
        supjLabel9.setText("SEARCH BY :");

        supjComboBox3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        supjComboBox3.setForeground(new java.awt.Color(255, 255, 255));
        supjComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "id", "name" }));

        supjTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                supjTextField5KeyReleased(evt);
            }
        });

        supDemo.setText("DEMO");
        supDemo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supDemoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout supAddDetailsLayout = new javax.swing.GroupLayout(supAddDetails);
        supAddDetails.setLayout(supAddDetailsLayout);
        supAddDetailsLayout.setHorizontalGroup(
            supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supAddDetailsLayout.createSequentialGroup()
                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(supAddDetailsLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(supAddDetailsLayout.createSequentialGroup()
                                .addComponent(supAddbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(supUpdatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(supDeletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(supAddDetailsLayout.createSequentialGroup()
                                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(supNolabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(supIdlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(supNamelabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(supEmaillabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(supAddresslabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(48, 48, 48)
                                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(supID, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(supName, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                        .addComponent(supEmail)
                                        .addComponent(supPno)
                                        .addComponent(supAddress))))))
                    .addGroup(supAddDetailsLayout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(supDemo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(supTble, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(supAddDetailsLayout.createSequentialGroup()
                        .addComponent(supjLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(supjComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(supjTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(952, Short.MAX_VALUE))
        );
        supAddDetailsLayout.setVerticalGroup(
            supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supAddDetailsLayout.createSequentialGroup()
                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(supAddDetailsLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, supAddDetailsLayout.createSequentialGroup()
                                .addComponent(supjLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(supjComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(supjTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addComponent(supTble, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(supAddDetailsLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(supAddDetailsLayout.createSequentialGroup()
                                .addComponent(supDemo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(supIdlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(supID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(supNamelabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(supName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(supEmaillabel)
                                    .addComponent(supEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(supNolabel)
                                    .addComponent(supPno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(supAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(supAddresslabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17)
                                .addGroup(supAddDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(supAddbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(supUpdatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(supDeletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(6787, Short.MAX_VALUE))
        );

        jPanel3.add(supAddDetails, "supAddDetails");

        empLeaveCard.setBackground(new java.awt.Color(153, 153, 153));

        empRemLeaveLabel.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empRemLeaveLabel.setForeground(new java.awt.Color(255, 255, 255));
        empRemLeaveLabel.setText("Remaining Annual Leave: ");

        empRemLeavBox.setBackground(new java.awt.Color(51, 51, 51));
        empRemLeavBox.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empRemLeavBox.setForeground(new java.awt.Color(255, 255, 255));
        empRemLeavBox.setBorder(null);
        empRemLeavBox.setDisabledTextColor(new java.awt.Color(255, 255, 255));

        empstartDateLabel.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empstartDateLabel.setForeground(new java.awt.Color(255, 255, 255));
        empstartDateLabel.setText("Start Date");

        empEndDateLabel.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empEndDateLabel.setForeground(new java.awt.Color(255, 255, 255));
        empEndDateLabel.setText("End Date");

        empDaysLabel.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empDaysLabel.setForeground(new java.awt.Color(255, 255, 255));
        empDaysLabel.setText("Number of Days");

        empDaysBox.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        empReasonLabel.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empReasonLabel.setForeground(new java.awt.Color(255, 255, 255));
        empReasonLabel.setText("Reason");

        empReasonText.setColumns(20);
        empReasonText.setRows(5);
        jScrollPane4.setViewportView(empReasonText);

        empLeaveRequestBtn.setBackground(new java.awt.Color(0, 102, 51));
        empLeaveRequestBtn.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empLeaveRequestBtn.setForeground(new java.awt.Color(255, 255, 255));
        empLeaveRequestBtn.setText("Send Request");
        empLeaveRequestBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empLeaveRequestBtnActionPerformed(evt);
            }
        });

        empRequestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        empRequestTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empRequestTableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(empRequestTable);

        empLeaveStatusLabel.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empLeaveStatusLabel.setForeground(new java.awt.Color(255, 255, 255));

        empLeaveStatusBox.setEditable(false);
        empLeaveStatusBox.setBackground(new java.awt.Color(51, 51, 51));
        empLeaveStatusBox.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empLeaveStatusBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        empLeaveStatusBox.setBorder(null);

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        empLeaveStartDate.setDateFormatString("yyyy-MM-dd");

        empLeaveEndDate.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout empLeaveCardLayout = new javax.swing.GroupLayout(empLeaveCard);
        empLeaveCard.setLayout(empLeaveCardLayout);
        empLeaveCardLayout.setHorizontalGroup(
            empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empLeaveCardLayout.createSequentialGroup()
                .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(empLeaveCardLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(empReasonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empDaysLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empEndDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empRemLeaveLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empstartDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveStatusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empRemLeavBox, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empDaysBox, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(124, 124, 124)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(empLeaveCardLayout.createSequentialGroup()
                        .addGap(602, 602, 602)
                        .addComponent(empLeaveRequestBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(469, Short.MAX_VALUE))
        );
        empLeaveCardLayout.setVerticalGroup(
            empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empLeaveCardLayout.createSequentialGroup()
                .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(empLeaveCardLayout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(empLeaveCardLayout.createSequentialGroup()
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(empLeaveCardLayout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(empRemLeaveLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empRemLeavBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(empstartDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empLeaveStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(empLeaveCardLayout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addComponent(empEndDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, empLeaveCardLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(empLeaveEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(51, 51, 51)))
                                .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(empDaysLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empDaysBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(empReasonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(empLeaveCardLayout.createSequentialGroup()
                                        .addGap(70, 70, 70)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(27, 27, 27)
                                .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(empLeaveStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empLeaveStatusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(empLeaveCardLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0)
                        .addComponent(empLeaveRequestBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6581, Short.MAX_VALUE))
        );

        jPanel3.add(empLeaveCard, "empLeaveCard");

        addEmpCard1.setBackground(new java.awt.Color(153, 153, 153));

        addEmpTitleTxt.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        addEmpTitleTxt.setForeground(new java.awt.Color(255, 255, 255));
        addEmpTitleTxt.setText("Add/Edit Employee ");

        empNameLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        empNameLabel.setText("Name");

        empNameBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        empNICBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        empNicLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empNicLabel.setForeground(new java.awt.Color(255, 255, 255));
        empNicLabel.setText("NIC");

        empAddressLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empAddressLabel.setForeground(new java.awt.Color(255, 255, 255));
        empAddressLabel.setText("Address");

        empAddressBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empAddressBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empAddressBoxActionPerformed(evt);
            }
        });

        empPhoneLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empPhoneLabel.setForeground(new java.awt.Color(255, 255, 255));
        empPhoneLabel.setText("Phone");

        empPhoneBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        empEmailLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empEmailLabel.setForeground(new java.awt.Color(255, 255, 255));
        empEmailLabel.setText("Email");

        empEmailBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        empTypeLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empTypeLabel.setForeground(new java.awt.Color(255, 255, 255));
        empTypeLabel.setText("Type");

        emptypeComboBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        emptypeComboBox.setForeground(new java.awt.Color(51, 51, 51));
        emptypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cashier", "Manager", "Driver", "Labourer" }));

        deleteEmpBtn.setBackground(new java.awt.Color(160, 31, 38));
        deleteEmpBtn.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        deleteEmpBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteEmpBtn.setText("Delete");
        deleteEmpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEmpBtnActionPerformed(evt);
            }
        });

        registerEmpBtn1.setBackground(new java.awt.Color(0, 102, 51));
        registerEmpBtn1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        registerEmpBtn1.setForeground(new java.awt.Color(255, 255, 255));
        registerEmpBtn1.setText("Register");
        registerEmpBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerEmpBtn1ActionPerformed(evt);
            }
        });

        updateEmpBtn.setBackground(new java.awt.Color(28, 47, 123));
        updateEmpBtn.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        updateEmpBtn.setForeground(new java.awt.Color(255, 255, 255));
        updateEmpBtn.setText("Update");
        updateEmpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEmpBtnActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        empTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        empTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(empTable);

        emIDLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        emIDLabel.setForeground(new java.awt.Color(255, 255, 255));
        emIDLabel.setText("ID");

        empIdBox.setEditable(false);
        empIdBox.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        empIdBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empIdBoxActionPerformed(evt);
            }
        });

        EmpSearchBarField.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        EmpSearchBarField.setForeground(new java.awt.Color(102, 102, 102));
        EmpSearchBarField.setText(" Search........");
        EmpSearchBarField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EmpSearchBarFieldMouseClicked(evt);
            }
        });
        EmpSearchBarField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpSearchBarFieldActionPerformed(evt);
            }
        });
        EmpSearchBarField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                EmpSearchBarFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout addEmpCard1Layout = new javax.swing.GroupLayout(addEmpCard1);
        addEmpCard1.setLayout(addEmpCard1Layout);
        addEmpCard1Layout.setHorizontalGroup(
            addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addEmpCard1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(addEmpCard1Layout.createSequentialGroup()
                            .addComponent(registerEmpBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(updateEmpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteEmpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addEmpCard1Layout.createSequentialGroup()
                                .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(empAddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empNicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empPhoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(emptypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(empPhoneBox, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(empNICBox)
                                        .addComponent(empAddressBox)
                                        .addComponent(empEmailBox, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(addEmpCard1Layout.createSequentialGroup()
                                    .addComponent(empNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(empNameBox, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(addEmpTitleTxt))))
                    .addGroup(addEmpCard1Layout.createSequentialGroup()
                        .addComponent(emIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(empIdBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(233, 233, 233)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
                    .addComponent(EmpSearchBarField))
                .addContainerGap(502, Short.MAX_VALUE))
        );
        addEmpCard1Layout.setVerticalGroup(
            addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addEmpCard1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jSeparator1)
                .addGap(35, 35, 35))
            .addGroup(addEmpCard1Layout.createSequentialGroup()
                .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addEmpCard1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(addEmpTitleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emIDLabel)
                            .addComponent(empIdBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empNameBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empNicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empNICBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empAddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empAddressBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(empPhoneBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empPhoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empEmailBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emptypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100)
                        .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteEmpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateEmpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registerEmpBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(addEmpCard1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(EmpSearchBarField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6739, Short.MAX_VALUE))
        );

        jPanel3.add(addEmpCard1, "addEmpCard1");

        smViewReports.setBackground(new java.awt.Color(153, 153, 153));

        smreportbtnsales.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        smreportbtnsales.setText("Report - Total Sales");
        smreportbtnsales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smreportbtnsalesActionPerformed(evt);
            }
        });

        smreportbtnbills.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        smreportbtnbills.setText("Report - Bill Records");
        smreportbtnbills.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smreportbtnbillsActionPerformed(evt);
            }
        });

        smreportbtnsalerec.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        smreportbtnsalerec.setText("Report - Sales Records");
        smreportbtnsalerec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smreportbtnsalerecActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout smViewReportsLayout = new javax.swing.GroupLayout(smViewReports);
        smViewReports.setLayout(smViewReportsLayout);
        smViewReportsLayout.setHorizontalGroup(
            smViewReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smViewReportsLayout.createSequentialGroup()
                .addGap(603, 603, 603)
                .addGroup(smViewReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(smreportbtnbills, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(smreportbtnsalerec, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(smreportbtnsales, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1072, Short.MAX_VALUE))
        );
        smViewReportsLayout.setVerticalGroup(
            smViewReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smViewReportsLayout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(smreportbtnsales, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(smreportbtnsalerec, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(smreportbtnbills, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6876, Short.MAX_VALUE))
        );

        jPanel3.add(smViewReports, "smViewReports");

        smRegisterCustomers.setBackground(new java.awt.Color(255, 255, 255));

        smtablebills.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "billId", "Date / Time", "Title 3", "Title 4"
            }
        ));
        smtablebills.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                smtablebillsMouseMoved(evt);
            }
        });
        jScrollPane22.setViewportView(smtablebills);

        smtablesales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        smtablesales.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                smtablesalesMouseMoved(evt);
            }
        });
        jScrollPane23.setViewportView(smtablesales);

        javax.swing.GroupLayout smRegisterCustomersLayout = new javax.swing.GroupLayout(smRegisterCustomers);
        smRegisterCustomers.setLayout(smRegisterCustomersLayout);
        smRegisterCustomersLayout.setHorizontalGroup(
            smRegisterCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smRegisterCustomersLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(smRegisterCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 1566, Short.MAX_VALUE)
                    .addComponent(jScrollPane23))
                .addContainerGap(466, Short.MAX_VALUE))
        );
        smRegisterCustomersLayout.setVerticalGroup(
            smRegisterCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smRegisterCustomersLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6601, Short.MAX_VALUE))
        );

        jPanel3.add(smRegisterCustomers, "smRegisterCustomers");

        dm_vehicleReport.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dm_vehicleReport.setText("Vehicle Report");
        dm_vehicleReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dm_vehicleReportActionPerformed(evt);
            }
        });

        dm_deliveryReport.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dm_deliveryReport.setText("Delivery Report");
        dm_deliveryReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dm_deliveryReportActionPerformed(evt);
            }
        });

        dm_reportTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dm_reportTitle.setText("Generate Report");

        javax.swing.GroupLayout dm_reportLayout = new javax.swing.GroupLayout(dm_report);
        dm_report.setLayout(dm_reportLayout);
        dm_reportLayout.setHorizontalGroup(
            dm_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dm_reportLayout.createSequentialGroup()
                .addGap(735, 735, 735)
                .addGroup(dm_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dm_deliveryReport, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dm_vehicleReport, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dm_reportTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1139, Short.MAX_VALUE))
        );
        dm_reportLayout.setVerticalGroup(
            dm_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dm_reportLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(dm_reportTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(dm_vehicleReport, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(dm_deliveryReport, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(6923, Short.MAX_VALUE))
        );

        jPanel3.add(dm_report, "dm_report");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 2075, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        viewPanel("sumPanel16");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        viewPanel("card2");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        viewPanel("card3");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        viewPanel("card5");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        viewPanel("card8");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        viewPanel("card6");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        viewPanel("card9");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        viewPanel("card7");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void submit_cuss_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_cuss_addActionPerformed
        // TODO add your handling code here:

        String name = cusNameAdd.getText();
        String phone = cusPhoneAdd.getText();
        String address = cusAddAdd.getText();
        String email = cusEmailAdd.getText();

        CustomersModel c1 = new CustomersModel(name, phone, address, email);

        int vali = 0;

        if (name == null || name.length() == 0) {
            vali = 1;
            name_val.setText("*Empty");
        } else {
            name_val.setText("");
        }
        if (phone == null || phone.length() == 0) {
            vali = 1;
            phone_val.setText("*Empty");
        } else if (phone.length() == 10 && phone.matches("[0-9]+")) {

            phone_val.setText("");
        } else {
            vali = 1;
            phone_val.setText("Not valid!");
        }
        if (email.matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$") && email != null && email.length() != 0) {
            email_val.setText("");
        } else {

            vali = 1;
            email_val.setText("Not valid!");
        }
        if (address == null || address.length() == 0) {
            vali = 1;
            address_val.setText("*Empty");
        } else {
            address_val.setText("");
        }
        if (vali == 0) {
            CusDB db = new CusDB();
            db.addCustomers(c1);
            message.setText("Sucessfully Added");
            CusDB.CustableLoad(cus_view_table);
        }
    }//GEN-LAST:event_submit_cuss_addActionPerformed

    private void reset_cus_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_cus_addActionPerformed
        // TODO add your handling code here:

        cusNameAdd.setText("");
        cusPhoneAdd.setText("");
        cusAddAdd.setText("");
        cusEmailAdd.setText("");
    }//GEN-LAST:event_reset_cus_addActionPerformed

    private void IM_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_search1ActionPerformed
        // TODO add your handling code here:
        try {
            String Report = "C:\\Users\\Chathura Harshanga\\Documents\\NetBeansProjects\\ITP\\test\\IN\\report1.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(Report);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connn);
            JasperViewer.viewReport(jasperPrint);
        } catch (Exception e) {
            System.out.println("view.NewJFrame.ReportsActionPerformed()" + e);
        }
    }//GEN-LAST:event_IM_search1ActionPerformed

    private void IM_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_addActionPerformed

        viewPanel("IM_add");
        IM_Atable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));
    }//GEN-LAST:event_IM_addActionPerformed

    private void IM_update_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_update_deleteActionPerformed

        viewPanel("IM_update");
        IM_Utable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));
    }//GEN-LAST:event_IM_update_deleteActionPerformed

    private void IM_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_searchActionPerformed

        viewPanel("IM_search");
        IM_Stable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));
    }//GEN-LAST:event_IM_searchActionPerformed

    private void empLeaveMgmtNavBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empLeaveMgmtNavBtnActionPerformed
        viewPanel("empLeaveMgmtPanel");
        globalEmpDB.empLeaveRequestTableLoad(empLeaveRequestTable);
    }//GEN-LAST:event_empLeaveMgmtNavBtnActionPerformed

    private void addEmpCatNavBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmpCatNavBtnActionPerformed
        viewPanel("addCatergoryPanel");
        globalEmpDB.empCatergoryTableLoad(empCatergoryTable);
    }//GEN-LAST:event_addEmpCatNavBtnActionPerformed

    private void regEmpNavBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regEmpNavBtnActionPerformed

        viewPanel("addEmpCard1");

        globalEmpDB.empTableLoad(empTable);

        ResultSet typeRS = globalEmpDB.getEmpCatergory();

        try {

            Vector<EmployeeType> vector = new Vector<>();

            while (typeRS.next()) {

                EmployeeType e1 = new EmployeeType(typeRS.getInt("id"), typeRS.getString("description"));

                vector.addElement(e1);

            }

            emptypeComboBox.setModel(new DefaultComboBoxModel(vector));

        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_regEmpNavBtnActionPerformed

    private void leaveEmpNavBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveEmpNavBtnActionPerformed

        viewPanel("empLeaveCard");

        globalEmpDB.empLeaveRequestTableLoad(empRequestTable);

        //calc remaining leave
        int remLeave = globalEmpDB.calcRemLeave(5);

        empRemLeavBox.setText(Integer.toString(remLeave));

        empLeaveStatusBox.setBackground(Color.DARK_GRAY);
        empLeaveStatusLabel.setText("");
        empLeaveStatusBox.setText("");
    }//GEN-LAST:event_leaveEmpNavBtnActionPerformed

    private void attendanceEmpNavBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendanceEmpNavBtnActionPerformed

        viewPanel("attendanceEmpCard");
        globalEmpDB.empAttendanceTableLoad(jTable1);

        //set system date for attendance
        empDateLabel.setText(java.util.Calendar.getInstance().getTime().toString());
    }//GEN-LAST:event_attendanceEmpNavBtnActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        if (jRadioButton1.isSelected()) {

            //String se = jTextField3.getText();
            //int nu = Integer.parseInt(se);
            try {
                Query q = ITPPUEntityManager.
                        //createQuery("SELECT b FROM Billpayment b WHERE b.month like :pTitle");
                        createQuery("SELECT b FROM Billpayment b WHERE b.month like :pTitle");
                q.setParameter("pTitle", Integer.parseInt(jTextField3.getText()));
                //q.setParameter("pTitle", "%" + jTextField3.getText() + "%");
                billpaymentList.clear();
                billpaymentList = q.getResultList();
                refreshTable2(billpaymentList);

            } catch (NumberFormatException e) {
                refreshTable2(billpaymentList);
            }

        } else if (jRadioButton2.isSelected()) {

            Query q = ITPPUEntityManager.
                    createQuery("SELECT b FROM Book b WHERE b.author like :pAuthor");
            q.setParameter("pAuthor", "%" + jTextField3.getText() + "%");
            billpaymentList.clear();
            billpaymentList = q.getResultList();
            refreshTable2(billpaymentList);

        } else if (jRadioButton3.isSelected()) {

            Query q = ITPPUEntityManager.
                    createQuery("SELECT b FROM Book b WHERE b.publisher like :pPublisher");
            q.setParameter("pPublisher", "%" + jTextField3.getText() + "%");
            billpaymentList.clear();
            billpaymentList = q.getResultList();
            refreshTable2(billpaymentList);

        } else {
            JOptionPane.showMessageDialog(rootPane, "Select a criteria",
                    "Info", 0);
        }

    }//GEN-LAST:event_jTextField3KeyReleased

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        String amount = jTextField2.getText();
        String month = jTextField1.getText();

        try {

            if (amount.equals("") || month.equals("")) {

                JOptionPane.showMessageDialog(null, "Please fill all form fields!");

            } else {

                Billpayment b = ITPPUEntityManager.find(Billpayment.class, Integer.parseInt(jTextField4.getText()));

                int r = JOptionPane.showConfirmDialog(rootPane, "Update" + b.getPid(), "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

                if (r == JOptionPane.YES_OPTION) {
                    b.setType((Billtype) jComboBox1.getSelectedItem());
                    b.setMonth(Integer.parseInt(jTextField1.getText()));
                    b.setAmount(Integer.parseInt(jTextField2.getText()));
                    b.setEid((Employee) jComboBox2.getSelectedItem());

                    ITPPUEntityManager.getTransaction().begin();
                    ITPPUEntityManager.persist(b);
                    ITPPUEntityManager.getTransaction().commit();
                    JOptionPane.showMessageDialog(rootPane, "Record Updated", "Info", 1);

                    billpaymentList.clear();
                    billpaymentList = billpaymentQuery.getResultList();
                    refreshTable(billpaymentList);

                }
            }
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Please ensure amount and month are numneric!");
        }

    }//GEN-LAST:event_jButton14ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        Billpayment b = ITPPUEntityManager.find(Billpayment.class, Integer.parseInt(jTextField4.getText()));

        int r = JOptionPane.showConfirmDialog(rootPane, "Remove : " + b.getPid(),
                "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            ITPPUEntityManager.getTransaction().begin();
            ITPPUEntityManager.remove(b);
            ITPPUEntityManager.getTransaction().commit();
            JOptionPane.showMessageDialog(rootPane, "Record Removed", "Info", 1);

            billpaymentList.clear();
            billpaymentList = billpaymentQuery.getResultList();
            refreshTable(billtypeList);
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        Billpayment b = new Billpayment();

        try {
            String amount = jTextField2.getText();
            String month = jTextField1.getText();

            if (amount.equals("") || month.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all form fields!");
            } else {
                b.setType((Billtype) jComboBox1.getSelectedItem());
                b.setMonth(Integer.parseInt(jTextField1.getText()));
                b.setAmount(Integer.parseInt(jTextField2.getText()));
                b.setEid((Employee) jComboBox2.getSelectedItem());

                ITPPUEntityManager.getTransaction().begin();
                ITPPUEntityManager.persist(b);
                ITPPUEntityManager.getTransaction().commit();

                JOptionPane.showMessageDialog(rootPane, "Record inserted", "Info", 1);

                billpaymentList.clear();
                billpaymentList = billpaymentQuery.getResultList();
                refreshTable(billtypeList);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Amount and month must be numeric!");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void IM_search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_search_btnActionPerformed

        IM_Service service = new IM_Service();

        String by = IM_Sby.getSelectedItem().toString();
        String key = IM_key.getText();

        IM_Stable.setModel(DbUtils.resultSetToTableModel(service.search_item(by, key)));
    }//GEN-LAST:event_IM_search_btnActionPerformed

    private void cus_view_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cus_view_tableMouseClicked
        // TODO add your handling code here:
        int r = cus_view_table.getSelectedRow();
        String id = cus_view_table.getValueAt(r, 0).toString();
        String name = cus_view_table.getValueAt(r, 1).toString();
        String phone = cus_view_table.getValueAt(r, 2).toString();
        String email = cus_view_table.getValueAt(r, 3).toString();
        String address = cus_view_table.getValueAt(r, 4).toString();

        name_cus_u.setText(name);
        phoneBoxCus.setText(phone);
        adress_cus_u.setText(address);
        emailBoxCus.setText(email);
        labCusID.setText(id);
    }//GEN-LAST:event_cus_view_tableMouseClicked

    private void update_cus_uActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_cus_uActionPerformed
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(null, "Do you really want to update");

        if (x == 0) {
            String id = labCusID.getText();
            String name = name_cus_u.getText();
            String phone = phoneBoxCus.getText();
            String email = emailBoxCus.getText();
            String address = adress_cus_u.getText();

            CustomersModel c1 = new CustomersModel(id, name, phone, address, email);

            int vali = 0;

            if (name == null || name.length() == 0) {
                vali = 1;
                name_val_U.setText("*Empty");
            } else {
                name_val_U.setText("");
            }
            if (phone == null || phone.length() == 0) {
                vali = 1;
                phone_val_U1.setText("*Empty");
            } else if (phone.length() == 10 && phone.matches("[0-9]+")) {

                phone_val_U1.setText("");
            } else {
                vali = 1;
                phone_val_U1.setText("Not valid!");
            }
            if (email.matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$") && email != null && email.length() != 0) {
                email_val_U3.setText("");
            } else {

                vali = 1;
                email_val_U3.setText("Not valid!");
            }
            if (address == null || address.length() == 0) {
                vali = 1;
                address_val_U2.setText("*Empty");
            } else {
                address_val_U2.setText("");
            }
            if (vali == 0) {
                CusDB db = new CusDB();
                db.updateCustomers(c1);
                CusDB.CustableLoad(cus_view_table);

            }
        }
    }//GEN-LAST:event_update_cus_uActionPerformed

    private void delete_cus_UActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_cus_UActionPerformed
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(null, "Do you really want to update");

        if (x == 0) {
            String id = labCusID.getText();

            CustomersModel c1 = new CustomersModel(id);

            CusDB db = new CusDB();
            db.deleteCustomers(c1);
            CusDB.CustableLoad(cus_view_table);

        }
    }//GEN-LAST:event_delete_cus_UActionPerformed

    private void cancel_cusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_cusActionPerformed
        // TODO add your handling code here:
        name_cus_u.setText("");
        phoneBoxCus.setText("");
        adress_cus_u.setText("");
        emailBoxCus.setText("");
        labCusID.setText("Customer ID");
    }//GEN-LAST:event_cancel_cusActionPerformed

    private void smbtndeleteitemreturnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smbtndeleteitemreturnsActionPerformed
        // TODO add your handling code here:
        SMModel sm1 = new SMModel();
        sm1.setsalesId(Integer.parseInt(smsalesidreturnsbox.getText()));
        sm1.setBillId(Integer.parseInt(smtxtfieldenterbillid.getText()));
        SMServiceImpl sms1 = new SMServiceImpl();
        sms1.deleteitemfrmsales(sm1);
        sms1.getbillitemsforreturns(sm1);
        smviewreturnstable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitemsforreturns(sm1)));
    }//GEN-LAST:event_smbtndeleteitemreturnsActionPerformed

    private void smbtnsearchbillidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smbtnsearchbillidActionPerformed
        // TODO add your handling code here:
        try {

            String billid = smtxtfieldenterbillid.getText();

            if (billid.equals("")) {

                JOptionPane.showMessageDialog(null, "Please fill all the fields!");

            }
            int billidint = Integer.parseInt(smtxtfieldenterbillid.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Must be numeric!");
        }
        SMModel sm1 = new SMModel();
        sm1.setBillId(Integer.parseInt(smtxtfieldenterbillid.getText()));
        SMServiceImpl sms1 = new SMServiceImpl();
        sms1.getbillitemsforreturns(sm1);
        smviewreturnstable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitemsforreturns(sm1)));


    }//GEN-LAST:event_smbtnsearchbillidActionPerformed

    private void smviewreturnstableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smviewreturnstableMouseClicked
        // TODO add your handling code here:
        int smrowselected = smviewreturnstable.getSelectedRow();
        String smsalesidr;
        String smitemidr;
        String smqtyr;
        smsalesidr = smviewreturnstable.getValueAt(smrowselected, 0).toString();
        smitemidr = smviewreturnstable.getValueAt(smrowselected, 1).toString();
        smqtyr = smviewreturnstable.getValueAt(smrowselected, 2).toString();
        smsalesidreturnsbox.setText(smsalesidr);
        smtxtitemid.setText(smitemidr);
        smtxtitemqty.setText(smqtyr);
    }//GEN-LAST:event_smviewreturnstableMouseClicked

    private void empSignInBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empSignInBtnActionPerformed

        String popupMsg = "Sign in for " + java.util.Calendar.getInstance().getTime().toString() + " ?";

        int signInResponse = JOptionPane.showConfirmDialog(null, popupMsg);

        //temp hard coded id must be changed
        int id = 5;

        if (signInResponse == 0) {

            globalEmpDB.empSignIn(id);

        }

        globalEmpDB.empAttendanceTableLoad(empAttendanceTable);
    }//GEN-LAST:event_empSignInBtnActionPerformed

    private void empSignOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empSignOutBtnActionPerformed

        String popupMsg = "Sign out for " + java.util.Calendar.getInstance().getTime().toString() + " ?";

        int signInResponse = JOptionPane.showConfirmDialog(null, popupMsg);

        //temp hard coded id must be changed
        int id = 5;

        if (signInResponse == 0) {

            globalEmpDB.empSignIn(id);

        }

        globalEmpDB.empAttendanceTableLoad(jTable1);
    }//GEN-LAST:event_empSignOutBtnActionPerformed

    private void btnGenerateBillsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateBillsActionPerformed
        // TODO add your handling code here:
        custp.setText("");
        smCustIdStatus.setText("");
        smdisref.setText("");
        smitemqty.setText("");
        smqtystatus.setText("");
        smdisrefstatus.setText("");
        smitemid.setText("");
        smitemqty.setText("");
        SMServiceImpl sms1 = new SMServiceImpl();
        sms1.deleteallfrmbill();
        smbilltable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitems()));

        viewPanel("smGenerateBills");
    }//GEN-LAST:event_btnGenerateBillsActionPerformed

    private void btnRegisterCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterCustomersActionPerformed
        // TODO add your handling code here:
        viewPanel("smRegisterCustomers");
    }//GEN-LAST:event_btnRegisterCustomersActionPerformed

    private void btnHandleReturnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHandleReturnsActionPerformed
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        smviewreturnstable.setModel(DbUtils.resultSetToTableModel(sms1.deleteallfrmsalestable()));
        smsalesidreturnsbox.setText("");
        smtxtitemid.setText("");
        smtxtitemqty.setText("");
        smtxtfieldenterbillid.setText("");
        viewPanel("smReturnHandle");
    }//GEN-LAST:event_btnHandleReturnsActionPerformed

    private void btnViewReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewReportsActionPerformed
        // TODO add your handling code here:
        viewPanel("smViewReports");
    }//GEN-LAST:event_btnViewReportsActionPerformed

    private void IM_delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_delete_btnActionPerformed

        int ans = JOptionPane.showConfirmDialog(null, "are you sure you want to delete selected recode ?");

        IM_Service service = new IM_Service();

        String Iid = I_ID.getText();

        service.update_items(Iid, ans);
        IM_Utable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));
        IM_U_delete.setText("item deleted");
    }//GEN-LAST:event_IM_delete_btnActionPerformed

    private void IM_UtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IM_UtableMouseClicked

        int IM_row = IM_Utable.getSelectedRow();

        String ID = IM_Utable.getValueAt(IM_row, 0).toString();
        String Iname = IM_Utable.getValueAt(IM_row, 1).toString();
        String Itype = IM_Utable.getValueAt(IM_row, 2).toString();
        String Iqty = IM_Utable.getValueAt(IM_row, 3).toString();
        String IBprice = IM_Utable.getValueAt(IM_row, 4).toString();
        String ISprice = IM_Utable.getValueAt(IM_row, 5).toString();

        I_ID.setText(ID);
        I_Uname.setText(Iname);
        I_Utype.setText(Itype);
        I_Uqty.setText(Iqty);
        I_UBprice.setText(IBprice);
        I_USprice.setText(ISprice);

        IM_U_Bprice_error_lbl.setText("");
        IM_U_Sprice_error_lbl.setText("");
        IM_U_qty_error_lbl.setText("");
        IM_U_brand_error_lbl.setText("");
        IM_U_name_error_lbl.setText("");
        IM_U_update.setText("");
        IM_U_delete.setText("");
    }//GEN-LAST:event_IM_UtableMouseClicked

    private void IM_update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_update_btnActionPerformed

        int ans = JOptionPane.showConfirmDialog(null, "are you sure you want to update current recode ?");
        int Uvalid = 0;
        IM_Service service = new IM_Service();

        String Iid = I_ID.getText();
        String Iname = I_Uname.getText();
        String Itype = I_Utype.getText();
        int Iqty = 0;
        String IBprice = I_UBprice.getText();
        String ISprice = I_USprice.getText();

        if (Iname == null || Iname.length() == 0) {

            IM_U_name_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        } else {

            IM_U_name_error_lbl.setText("");

        }

        if (Itype == null || Itype.length() == 0) {

            IM_U_brand_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        } else {

            IM_U_brand_error_lbl.setText("");

        }

        if (ISprice == null || ISprice.length() == 0) {

            IM_U_Sprice_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        } else {

            IM_U_Sprice_error_lbl.setText("");

        }

        if (I_Uqty.getText() == null || I_Uqty.getText().length() == 0) {

            IM_U_qty_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        } else {

            Iqty = Integer.parseInt(I_Uqty.getText());
            IM_U_qty_error_lbl.setText("");
        }

        if (IBprice == null || IBprice.length() == 0) {

            IM_U_Bprice_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        } else {

            IM_U_Bprice_error_lbl.setText("");
        }

        if (Iqty < 0) {

            IM_U_qty_error_lbl.setText("*invalid qty");
            Uvalid = 1;

        }
        try {

            if (Double.parseDouble(IBprice) < 0) {

                IM_U_Bprice_error_lbl.setText("*invalid ammount");
                Uvalid = 1;

            }

            if (Double.parseDouble(ISprice) < 0) {

                IM_U_Sprice_error_lbl.setText("*invalid ammount");
                Uvalid = 1;

            }

            if (Uvalid == 0) {

                service.update_items(ans, Iid, Iname, Itype, Iqty, IBprice, ISprice);
                IM_U_Bprice_error_lbl.setText("");
                IM_U_Sprice_error_lbl.setText("");
                IM_U_qty_error_lbl.setText("");
                IM_U_brand_error_lbl.setText("");
                IM_U_name_error_lbl.setText("");

                IM_Utable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));
                IM_U_update.setText("item added successfully");

            }
        } catch (Exception e) {
            System.out.println("e");
            IM_U_Sprice_error_lbl.setText("*this field is empty");
            IM_U_Bprice_error_lbl.setText("*this field is empty");
        }
    }//GEN-LAST:event_IM_update_btnActionPerformed

    private void SuMAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuMAddActionPerformed
        // TODO add your handling code here:
        globalSupDB.supTableLoad(supTable);
        viewPanel("supAddDetails");
    }//GEN-LAST:event_SuMAddActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        viewPanel("pmSearchBillPayments");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        viewPanel("card10");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void smresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smresetActionPerformed
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        sms1.deleteallfrmbill();
        smbilltable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitems()));
    }//GEN-LAST:event_smresetActionPerformed

    private void smbilltableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smbilltableMouseClicked
        // TODO add your handling code here:
        int smrow = smbilltable.getSelectedRow();
        String smitemid1;
        String smqty1;
        smitemid1 = smbilltable.getValueAt(smrow, 0).toString();
        smqty1 = smbilltable.getValueAt(smrow, 2).toString();
        smitemid.setText(smitemid1);
        smitemqty.setText(smqty1);
    }//GEN-LAST:event_smbilltableMouseClicked

    private void smbtndeleteitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smbtndeleteitemActionPerformed
        // TODO add your handling code here:
        SMModel sm1 = new SMModel();
        sm1.setItemId(Integer.parseInt(smitemid.getText()));
        SMServiceImpl sms1 = new SMServiceImpl();
        sms1.deleteitemfrmbill(sm1);
        smbilltable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitems()));
    }//GEN-LAST:event_smbtndeleteitemActionPerformed

    private void smbtnadditemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smbtnadditemActionPerformed
        // TODO add your handling code here:
        SMModel sm1 = new SMModel();
        sm1.setItemId(Integer.parseInt(smitemid.getText()));
        sm1.setQty(Integer.parseInt(smitemqty.getText()));
        SMServiceImpl sms1 = new SMServiceImpl();
        if (sms1.addtobill(sm1)) {
            smqtystatus.setText("");
        } else {
            smqtystatus.setText("Error");
        }

        smbilltable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitems()));
    }//GEN-LAST:event_smbtnadditemActionPerformed

    private void smitemqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smitemqtyKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_smitemqtyKeyTyped

    private void smitemqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smitemqtyKeyReleased
        // TODO add your handling code here:
        try {
            int qtyno = Integer.parseInt(smitemqty.getText());
            smqtystatus.setText("");

        } catch (NumberFormatException e) {
            smqtystatus.setText("Enter a Valid Value");
        }
    }//GEN-LAST:event_smitemqtyKeyReleased

    private void smitemqtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smitemqtyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_smitemqtyKeyPressed

    private void smdisrefKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smdisrefKeyReleased
        // TODO add your handling code here:
        try {
            double disref = Double.parseDouble(smdisref.getText());
            smdisrefstatus.setText("");

        } catch (NumberFormatException e) {
            smdisrefstatus.setText("Enter a Valid Value");
        }
    }//GEN-LAST:event_smdisrefKeyReleased

    private void btnNxtGenerateBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNxtGenerateBillActionPerformed
        // TODO add your handling code here:
        smtotbillamt.setText("");
        smcashamtpaid.setText("");
        smchangedis.setText("");

        String custpstring = custp.getText();
        String disrefstring = smdisref.getText();

        if (custpstring.equals("") || disrefstring.equals("")) {

            JOptionPane.showMessageDialog(null, "Please fill all the fields!");
        }

        SMModel sm1 = new SMModel();
        sm1.setcustp(custp.getText());
        SMServiceImpl sms1 = new SMServiceImpl();
        if (sms1.customerTableLoad(sm1)) {
            smCustIdStatus.setText(" ");
            SMModel sm = new SMModel();
            sm.setcustp(custp.getText());
            sm.setReturnAmt(Double.parseDouble(smdisref.getText()));
            sm.setPayType((String) custpaymentmethod.getSelectedItem());

            sms1.generatebill(sm);
            sms1.deleteallfrmbill();
            viewPanel("smGenerateBills2");
        } else {
            smCustIdStatus.setText("Invalid Customer, Use Anonymous Id or Register Customer");
        }
    }//GEN-LAST:event_btnNxtGenerateBillActionPerformed

    private void smchangedisMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smchangedisMouseMoved
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        smtotbillamt.setText(Double.toString(sms1.returnbillamt()));
        String smcashpaid = smcashamtpaid.getText();
        smchangedis.setText(Double.toString(-sms1.returnbillamt() + Double.parseDouble(smcashpaid)));
    }//GEN-LAST:event_smchangedisMouseMoved

    private void smtotbillamtMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smtotbillamtMouseMoved
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        smtotbillamt.setText(Double.toString(sms1.returnbillamt()));
        String smcashpaid = smcashamtpaid.getText();
        smchangedis.setText(Double.toString(-sms1.returnbillamt() + Double.parseDouble(smcashpaid)));
    }//GEN-LAST:event_smtotbillamtMouseMoved

    private void empCatergoryTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empCatergoryTableMouseClicked

        int selectedRow = empCatergoryTable.getSelectedRow();

        String typeId = empCatergoryTable.getValueAt(selectedRow, 0).toString();
        String type = empCatergoryTable.getValueAt(selectedRow, 1).toString();
        String basicSal = empCatergoryTable.getValueAt(selectedRow, 2).toString();
        String vacDays = empCatergoryTable.getValueAt(selectedRow, 3).toString();

        empTypeIdBox.setText(typeId);
        empTypeBox1.setText(type);
        empSalaryBox.setText(basicSal);
        empLeaveBox.setText(vacDays);
    }//GEN-LAST:event_empCatergoryTableMouseClicked

    private void addEmpTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmpTypeBtnActionPerformed

        try {
            String empType = empTypeBox1.getText();
            double empSalary = Double.parseDouble(empSalaryBox.getText());
            int empLeave = Integer.parseInt(empLeaveBox.getText());

            if (empType.equals("")) {

                JOptionPane.showMessageDialog(null, "Employee Type cannot be empty!");

            } else {

                EmployeeType empTypeObj = new EmployeeType(empType, empSalary, empLeave);

                globalEmpDB.addEmpType(empTypeObj);
                globalEmpDB.empCatergoryTableLoad(empCatergoryTable);
            }
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Salary and vaction days must be numeric!");

        }
    }//GEN-LAST:event_addEmpTypeBtnActionPerformed

    private void deleteEmpTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEmpTypeBtnActionPerformed

        int typeId = Integer.parseInt(empTypeIdBox.getText());

        globalEmpDB.deleteEmpType(typeId);

        globalEmpDB.empCatergoryTableLoad(empCatergoryTable);
    }//GEN-LAST:event_deleteEmpTypeBtnActionPerformed

    private void updateEmpTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEmpTypeBtnActionPerformed

        try {

            int typeId = Integer.parseInt(empTypeIdBox.getText());
            String typeName = empTypeBox1.getText();
            double basicSal = Double.parseDouble(empSalaryBox.getText());
            int vacationDays = Integer.parseInt(empLeaveBox.getText());

            if (typeName.equals("")) {

                JOptionPane.showMessageDialog(addEmpCard1, "Employee type cannot be empty!");
            } else {
                EmployeeType empType = new EmployeeType(typeName, basicSal, vacationDays);
                empType.setEmpTypeId(typeId);

                globalEmpDB.updateEmpType(empType);

                globalEmpDB.empCatergoryTableLoad(empCatergoryTable);
            }
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(addEmpCard1, "Please ensure salary and vacation days are numeric!");

        }
    }//GEN-LAST:event_updateEmpTypeBtnActionPerformed

    private void SuMOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuMOrderActionPerformed
        // TODO add your handling code here:
        globalSupDB.orderTableLoad(orderTable);
        viewPanel("supOrderDetails");

        ResultSet type = globalSupDB.getSupCategory();
        ResultSet type1 = globalSupDB.getItemCategory();

        try {

            Vector<SupplyModel> vector = new Vector<>();

            while (type.next()) {

                SupplyModel s1 = new SupplyModel(type.getInt("supplierId"), type.getString("supplierName"));

                vector.addElement(s1);

            }

            supIDcombo.setModel(new DefaultComboBoxModel(vector));

        } catch (SQLException ex) {

            System.out.println("ex");

        }

        try {

            Vector<ItemSupModel> vector2 = new Vector<>();

            while (type1.next()) {

                ItemSupModel i1 = new ItemSupModel(type1.getInt("itemId"), type1.getString("itemName"));

                vector2.addElement(i1);

            }

            Itemcombo.setModel(new DefaultComboBoxModel(vector2));

        } catch (SQLException ex) {

            System.out.println("ex");

        }
    }//GEN-LAST:event_SuMOrderActionPerformed

    private void IM_add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_add_btnActionPerformed

        IM_Service service = new IM_Service();

        int valid = 0;
        String Iname = I_name.getText();
        String Itype = I_type.getText();
        int Iqty = 0;
        double IBprice = 0;
        double ISprice = 0;

        if (Iname == null || Iname.length() == 0) {

            IM_A_name_error_lbl.setText("*this field is empty");
            valid = 1;

        } else {

            IM_A_name_error_lbl.setText("");

        }

        if (Itype == null || Itype.length() == 0) {

            IM_A_brand_error_lbl.setText("*this field is empty");
            valid = 1;

        } else {

            IM_A_brand_error_lbl.setText("");

        }

        if (I_Sprice.getText() == null || I_Sprice.getText().length() == 0) {

            IM_A_Sprice_error_lbl.setText("*this field is empty");
            valid = 1;

        } else {
            ISprice = Double.parseDouble(I_Sprice.getText());
            IM_A_Sprice_error_lbl.setText("");

        }

        if (I_qty.getText() == null || I_qty.getText().length() == 0) {

            IM_A_qty_error_lbl.setText("*this field is empty");
            valid = 1;

        } else {

            Iqty = Integer.parseInt(I_qty.getText());
            IM_A_qty_error_lbl.setText("");
        }

        if (I_Bprice.getText() == null || I_Bprice.getText().length() == 0) {

            IM_A_Bprice_error_lbl.setText("*this field is empty");
            valid = 1;

        } else {
            IBprice = Double.parseDouble(I_Bprice.getText());
            IM_A_Bprice_error_lbl.setText("");
        }

        if (Iqty < 0) {

            IM_A_qty_error_lbl.setText("*invalid qty");
            valid = 1;

        }

        if (IBprice < 0) {

            IM_A_Bprice_error_lbl.setText("*invalid ammount");
            valid = 1;

        }

        if (ISprice < 0) {

            IM_A_Sprice_error_lbl.setText("*invalid ammount");
            valid = 1;

        }

        if (valid == 0) {

            service.add_item(Iname, Itype, Iqty, IBprice, ISprice);
            IM_A_Bprice_error_lbl.setText("");
            IM_A_Sprice_error_lbl.setText("");
            IM_A_qty_error_lbl.setText("");
            IM_A_brand_error_lbl.setText("");
            IM_A_name_error_lbl.setText("");

            IM_Atable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));
            IM_A_success.setText("item added successfully");

        }
    }//GEN-LAST:event_IM_add_btnActionPerformed

    private void ManageCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageCustomersActionPerformed
        // TODO add your handling code here:
        CusDB.CustableLoad(cus_view_table);
        viewPanel("cmUDCus");
        System.out.println("I am in");

    }//GEN-LAST:event_ManageCustomersActionPerformed

    private void AddCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCustomersActionPerformed
        // TODO add your handling code here:
        viewPanel("cmAddCus");

    }//GEN-LAST:event_AddCustomersActionPerformed

    private void empRejectRequestBtn_mgmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empRejectRequestBtn_mgmtActionPerformed

        try {
            int requestId = Integer.parseInt(empLeaveReqID_mgmt.getText());

            globalEmpDB.rejectLeaveRequest(requestId);

            globalEmpDB.empLeaveRequestTableLoad(empLeaveRequestTable);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Please select a request first!");

        }
    }//GEN-LAST:event_empRejectRequestBtn_mgmtActionPerformed

    private void empLeaveRequestTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empLeaveRequestTableMouseClicked

        int leaveTableRow = empLeaveRequestTable.getSelectedRow();
        EmployeeModel e1;

        String empId = empLeaveRequestTable.getValueAt(leaveTableRow, 1).toString();
        String requestId = empLeaveRequestTable.getValueAt(leaveTableRow, 0).toString();
        String empName;
        String startDate = empLeaveRequestTable.getValueAt(leaveTableRow, 2).toString();
        String endDate = empLeaveRequestTable.getValueAt(leaveTableRow, 3).toString();
        String nDays = empLeaveRequestTable.getValueAt(leaveTableRow, 4).toString();
        String reason = empLeaveRequestTable.getValueAt(leaveTableRow, 7).toString();

        empID_mgmt1.setText(empId);
        empLeaveReqID_mgmt.setText(requestId);
        empstartDateBox_mgmt.setText(startDate);
        empEndDateBox_mgmt.setText(endDate);
        empDaysBox_mgmt.setText(nDays);
        empReasonText_mgmt.setText(reason);

        e1 = globalEmpDB.findEmpById(Integer.parseInt(empId));

        empEndDateBox_mgmt1.setText(e1.getName());
    }//GEN-LAST:event_empLeaveRequestTableMouseClicked

    private void empApproveRequestBtn_mgmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empApproveRequestBtn_mgmtActionPerformed

        try {
            int requestId = Integer.parseInt(empLeaveReqID_mgmt.getText());

            globalEmpDB.approveLeaveRequest(requestId);

            globalEmpDB.empLeaveRequestTableLoad(empLeaveRequestTable);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Please select a request first!");

        }
    }//GEN-LAST:event_empApproveRequestBtn_mgmtActionPerformed

    private void custpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_custpKeyReleased
        // TODO add your handling code here:
        SMModel sm1 = new SMModel();
        sm1.setcustp(custp.getText());
        SMServiceImpl sms1 = new SMServiceImpl();
        if (sms1.customerTableLoad(sm1)) {
            smCustIdStatus.setText(" ");
        } else {
            smCustIdStatus.setText("Invalid Customer, Use Anonymous Id or Register Customer");
        }

    }//GEN-LAST:event_custpKeyReleased

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        new admin_view().setVisible(true);

    }//GEN-LAST:event_jButton16ActionPerformed

    private void dmjButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmjButton5ActionPerformed
        // TODO add your handling code here:
        viewPanel("dmAddVehicle_Panel_AddVehicle");

    }//GEN-LAST:event_dmjButton5ActionPerformed

    private void dmjButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmjButton4ActionPerformed

//        // TODO add your handling code here:
        viewPanel("dm_report");

//        String path = "";
//        JFileChooser j = new JFileChooser();
//        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        int x = j.showSaveDialog(this);
//
//        if (x == JFileChooser.APPROVE_OPTION) {
//            path = j.getSelectedFile().getPath();
//        }
//
//        try {
//            Document document = new com.itextpdf.text.Document();
//            PdfWriter.getInstance(document, new FileOutputStream(path + "\\Report_Delivery_Vehicle.pdf"));
//            document.open();
//
//            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("D:\\Project images\\sliit.png");
//            //document.add(new Paragraph("image"));
//            document.add(image);
//
//            document.add(new Paragraph("Wijesinghe Motors", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.RED)));
//            document.add(new Paragraph(new Date().toString()));
//            document.add(new Paragraph("---------------------------------------------------------------------------------------"));
//
//            PdfPTable table = new PdfPTable(2);
//
//            PdfPCell cell = new PdfPCell(new Paragraph("Pay your fine!"));
//            cell.setColspan(4);
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBackgroundColor(BaseColor.GREEN);
//            table.addCell(cell);
//            
//            String id = jTextField34.getText();
//            String name = jTextField35.getText();
//            String fine = jTextField40.getText();
//
//            table.addCell("Member ID :");
//            table.addCell(id);
//            table.addCell("Name :");
//            table.addCell(name);
//            table.addCell("Fine :");
//            table.addCell("Rs. " +fine);
//            //table.addCell("item7");
//            document.add(table);
//
//            document.close();
//            JOptionPane.showMessageDialog(rootPane, "Fine bill generated");
//        } catch (Exception e) {
//
//        }

    }//GEN-LAST:event_dmjButton4ActionPerformed

    private void dmjButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmjButton3ActionPerformed
        // TODO add your handling code here:
        viewPanel("dmDeliveryDetails_Panel_DeliveryDetails");
        loaddata_deliveryDetails();

    }//GEN-LAST:event_dmjButton3ActionPerformed

    private void dmjButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmjButton2ActionPerformed
        // TODO add your handling code here:
        viewPanel("dmAddDelivery_Panel_AddDelivery");

    }//GEN-LAST:event_dmjButton2ActionPerformed

    private void dmjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmjButton1ActionPerformed
        // TODO add your handling code here:
        viewPanel("dmVehicleDetails_Panel_main");
        loadData_vehicleDetails();

    }//GEN-LAST:event_dmjButton1ActionPerformed

    private void dmAddDelivery_billIDPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_dmAddDelivery_billIDPopupMenuWillBecomeVisible
        if ("Select...".equals(dmAddDelivery_billID.getItemAt(0))) {
            dmAddDelivery_billID.removeItemAt(0);
        }
        if ("Select...".equals(dmAddDelivery_billID.getItemAt(dmAddDelivery_billID.getItemCount() - 1))) {
            dmAddDelivery_billID.removeItemAt(dmAddDelivery_billID.getItemCount() - 1);
        }
    }//GEN-LAST:event_dmAddDelivery_billIDPopupMenuWillBecomeVisible

    private void dmAddDelivery_custNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddDelivery_custNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddDelivery_custNameActionPerformed

    private void dmAddDelivery_timeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddDelivery_timeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddDelivery_timeActionPerformed

    private void dmAddDelivery_timeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dmAddDelivery_timeKeyPressed

    }//GEN-LAST:event_dmAddDelivery_timeKeyPressed

    private void dmAddDelivery_timeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dmAddDelivery_timeKeyReleased
        if (dmAddDelivery_time.getText().length() == 2) {
            dmAddDelivery_time.setText(dmAddDelivery_time.getText() + ":");
        }
    }//GEN-LAST:event_dmAddDelivery_timeKeyReleased

    private void dmAddDelivery_timeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dmAddDelivery_timeKeyTyped

        if (!(Character.isDigit(evt.getKeyChar()))) {
            evt.consume();
        }
        if (dmAddDelivery_time.getText().length() == 5) {
            evt.consume();
        }

    }//GEN-LAST:event_dmAddDelivery_timeKeyTyped

    private void dmAddDelivery_DeliveryIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddDelivery_DeliveryIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddDelivery_DeliveryIDActionPerformed

    private void dmAddDelivery_townActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddDelivery_townActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddDelivery_townActionPerformed

    private void dmAddDelivery_VehicleIdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dmAddDelivery_VehicleIdItemStateChanged
        if (dmAddDelivery_VehicleId.getSelectedIndex() != 0) {
            try {
                String qry = "SELECT delivery_vehicle.category FROM delivery_vehicle WHERE delivery_vehicle.vehicleID = '" + dmAddDelivery_VehicleId.getSelectedItem().toString() + "'";
                ResultSet rs = ShakithDBConnect.search(qry);
                rs.first();
                dmAddDelivery_type.setText(rs.getString("category"));
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_dmAddDelivery_VehicleIdItemStateChanged

    private void dmAddDelivery_VehicleIdPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_dmAddDelivery_VehicleIdPopupMenuWillBecomeVisible
        if ("Select...".equals(dmAddDelivery_VehicleId.getItemAt(0))) {
            dmAddDelivery_VehicleId.removeItemAt(0);
        }
        if ("Select...".equals(dmAddDelivery_VehicleId.getItemAt(dmAddDelivery_VehicleId.getItemCount() - 1))) {
            dmAddDelivery_VehicleId.removeItemAt(dmAddDelivery_VehicleId.getItemCount() - 1);
        }
    }//GEN-LAST:event_dmAddDelivery_VehicleIdPopupMenuWillBecomeVisible

    private void dmAddDelivery_DriverItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dmAddDelivery_DriverItemStateChanged

        if (dmAddDelivery_Driver.getSelectedIndex() != 0) {
            try {
                String qry = "SELECT employee.empName FROM employee WHERE employee.empId = '" + dmAddDelivery_Driver.getSelectedItem().toString() + "'";
                ResultSet rs = ShakithDBConnect.search(qry);
                rs.first();
                dmAddDelivery_Name.setText(rs.getString("empName"));
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_dmAddDelivery_DriverItemStateChanged

    private void dmAddDelivery_DriverPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_dmAddDelivery_DriverPopupMenuWillBecomeVisible
        if ("Select...".equals(dmAddDelivery_Driver.getItemAt(0))) {
            dmAddDelivery_Driver.removeItemAt(0);
        }
        if ("Select...".equals(dmAddDelivery_Driver.getItemAt(dmAddDelivery_Driver.getItemCount() - 1))) {
            dmAddDelivery_Driver.removeItemAt(dmAddDelivery_Driver.getItemCount() - 1);
        }
    }//GEN-LAST:event_dmAddDelivery_DriverPopupMenuWillBecomeVisible

    private void dmAddDelivery_NameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddDelivery_NameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddDelivery_NameActionPerformed

    private void dmAddDelivery_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddDelivery_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddDelivery_typeActionPerformed

    private void dmAddDelivery_btn_AddDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddDelivery_btn_AddDeliveryActionPerformed

        if (validateInput()) {
            Date d = dmAddDelivery_date.getDate();
            String date = new SimpleDateFormat("yyyy/MM/dd").format(d);

            String time = dmAddDelivery_time.getText() + " " + jComboBox2.getSelectedItem().toString();
            String qry = "INSERT INTO deliveries VALUES('" + dmAddDelivery_DeliveryID.getText() + "','" + dmAddDelivery_billID.getSelectedItem().toString() + "','" + dmAddDelivery_custName.getText() + "','" + date + "','" + time + "','" + dmAddDelivery_address.getText() + "','" + dmAddDelivery_town.getText() + "','" + dmAddDelivery_VehicleId.getSelectedItem().toString() + "','" + dmAddDelivery_Driver.getSelectedItem().toString() + "','Pending...')";
            JOptionPane.showMessageDialog(this, "Data Saved", "Details added successfully", JOptionPane.INFORMATION_MESSAGE);
            try {
                ShakithDBConnect.iud(qry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Data Not Saved", "Details not added successfully", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_dmAddDelivery_btn_AddDeliveryActionPerformed

    private void dmAddDelivery_btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddDelivery_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_dmAddDelivery_btnResetActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void dmAddVehicle_vehicleIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddVehicle_vehicleIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddVehicle_vehicleIdActionPerformed

    private void dmAddVehicle_registerNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddVehicle_registerNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddVehicle_registerNumActionPerformed

    private void dmAddVehicle_makeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddVehicle_makeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddVehicle_makeActionPerformed

    private void dmAddVehicle_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddVehicle_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddVehicle_dateActionPerformed

    private void dmAddVehicle_capacityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddVehicle_capacityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmAddVehicle_capacityActionPerformed

    private void dmAddVehicle_capacityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dmAddVehicle_capacityKeyTyped
        if (!(Character.isDigit(evt.getKeyChar()))) {
            evt.consume();
        }
    }//GEN-LAST:event_dmAddVehicle_capacityKeyTyped

    private void dmAddVehicle_btnChooseImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddVehicle_btnChooseImgActionPerformed
        try {
            JFileChooser jf = new JFileChooser("D:\\Wallpapers");
            int option = jf.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File f = jf.getSelectedFile();
                BufferedImage img = ImageIO.read(f);
                Image dimg = img.getScaledInstance(dmAddVehicle_Label_img.getWidth(), dmAddVehicle_Label_img.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                dmAddVehicle_Label_img.setIcon(imageIcon);
                path = f.toPath().toString().replace("\\", "\\\\");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_dmAddVehicle_btnChooseImgActionPerformed

    private void dmAddVehicle_btnAddVehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddVehicle_btnAddVehicleActionPerformed
        if (validateInput_addVehicle()) {
            try {
                String vehicleID = id;//Integer.parseInt(jTextField1.getText());
                String registrationNumber = dmAddVehicle_registerNum.getText();
                String make = dmAddVehicle_make.getSelectedItem().toString();
                String category = dmAddVehicle_category.getSelectedItem().toString();
                String fuelType = dmAddVehicle_fuelType.getSelectedItem().toString();
                int capacity = Integer.parseInt(dmAddVehicle_capacity.getText());
                String description = dmAddVehicle_desc.getText();
                String date = dmAddVehicle_date.getText();

                ShakithDBConnect.iud("INSERT INTO delivery_vehicle(vehicleID,registrationNumber,make,category,fuelType,capacity,description,date,path) VALUES ('" + vehicleID + "','" + registrationNumber + "','" + make + "','" + category + "','" + fuelType + "','" + capacity + "','" + description + "','" + date + "','" + path + "')");
                JOptionPane.showMessageDialog(this, "Data Saved", "Details added successfully", JOptionPane.INFORMATION_MESSAGE);
                reset_addVehicle();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Data Not Saved", "Details Not successfully", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Data Not Saved", "Details Not successfully", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_dmAddVehicle_btnAddVehicleActionPerformed

    private void dmAddVehicle_btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmAddVehicle_btnResetActionPerformed
        reset_addVehicle();
    }//GEN-LAST:event_dmAddVehicle_btnResetActionPerformed

    private void dmDeliveryDetails_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dmDeliveryDetails_SearchFocusGained
        if (dmDeliveryDetails_Search.getText().equals("Search Here") && dmDeliveryDetails_Search.getForeground().equals(new Color(204, 204, 204))) {
            dmDeliveryDetails_Search.setText(null);
            dmDeliveryDetails_Search.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_dmDeliveryDetails_SearchFocusGained

    private void dmDeliveryDetails_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dmDeliveryDetails_SearchFocusLost
        if (dmDeliveryDetails_Search.getText().isEmpty() && dmDeliveryDetails_Search.getForeground().equals(new Color(0, 0, 0))) {
            dmDeliveryDetails_Search.setText("Search Here");
            dmDeliveryDetails_Search.setForeground(new Color(204, 204, 204));
        }
    }//GEN-LAST:event_dmDeliveryDetails_SearchFocusLost

    private void dmDeliveryDetails_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetails_SearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmDeliveryDetails_SearchActionPerformed

    private void dmDeliveryDetails_SearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetails_SearchbtnActionPerformed
        if (dmDeliveryDetails_Search.getText().isEmpty() || (dmDeliveryDetails_Search.getText().equals("Search Here") && dmDeliveryDetails_Search.getForeground().equals(new Color(204, 204, 204)))) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) dmDeliveryDetails_table.getModel()));
            sorter.setRowFilter(RowFilter.regexFilter(""));
            dmDeliveryDetails_table.setRowSorter(sorter);

        }
        if (!(dmDeliveryDetails_Search.getText().isEmpty() || (dmDeliveryDetails_Search.getText().equals("Search Here") && dmDeliveryDetails_Search.getForeground().equals(new Color(204, 204, 204))))) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) dmDeliveryDetails_table.getModel()));
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + dmDeliveryDetails_Search.getText()));
            dmDeliveryDetails_table.setRowSorter(sorter);
        }
    }//GEN-LAST:event_dmDeliveryDetails_SearchbtnActionPerformed

    private void dmDeliveryDetails_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dmDeliveryDetails_tableMouseClicked
        DefaultTableModel dtm = (DefaultTableModel) dmDeliveryDetails_table.getModel();
        try {

            if (dtm.getValueAt(dmDeliveryDetails_table.getSelectedRow(), 3).toString().equals("true")) {
                ShakithDBConnect.iud("UPDATE deliveries SET deliveries.`status` = 'Delivered...' WHERE deliveryID='" + dtm.getValueAt(dmDeliveryDetails_table.getSelectedRow(), 0).toString() + "'");
            } else {
                ShakithDBConnect.iud("UPDATE deliveries SET deliveries.`status` = 'Pending...' WHERE deliveryID='" + dtm.getValueAt(dmDeliveryDetails_table.getSelectedRow(), 0).toString() + "'");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (evt.getClickCount() == 2) {

            //DeliveryDetailsDisc vd = new DeliveryDetailsDisc();
            vnum = dtm.getValueAt(dmDeliveryDetails_table.getSelectedRow(), 0).toString();
            setDetails_DeliveryDetails();
            viewPanel("dmDeliveryDetailsDisc_Panel_DeliveryDetals");

        }

    }//GEN-LAST:event_dmDeliveryDetails_tableMouseClicked

    private void dmDeliveryDetailsDisc_custNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetailsDisc_custNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmDeliveryDetailsDisc_custNameActionPerformed

    private void dmDeliveryDetailsDisc_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetailsDisc_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmDeliveryDetailsDisc_dateActionPerformed

    private void dmDeliveryDetailsDisc_timeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetailsDisc_timeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmDeliveryDetailsDisc_timeActionPerformed

    private void dmDeliveryDetailsDisc_billIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetailsDisc_billIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmDeliveryDetailsDisc_billIDActionPerformed

    private void dmDeliveryDetailsDisc_DeliveryIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetailsDisc_DeliveryIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmDeliveryDetailsDisc_DeliveryIDActionPerformed

    private void dmDeliveryDetailsDisc_townActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetailsDisc_townActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmDeliveryDetailsDisc_townActionPerformed

    private void dmDeliveryDetailsDisc_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetailsDisc_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmDeliveryDetailsDisc_typeActionPerformed

    private void dmDeliveryDetails_vehicleIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetails_vehicleIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmDeliveryDetails_vehicleIDActionPerformed

    private void dmDeliveryDetailsDisc_btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetailsDisc_btn_deleteActionPerformed

        int x = JOptionPane.showConfirmDialog(null, "Do you really want to Delete");
        if (x == 0) {
            try {
                String qry = "DELETE FROM deliveries WHERE deliveries.deliveryID = '" + dmDeliveryDetailsDisc_DeliveryID.getText() + "' ";
                ShakithDBConnect.iud(qry);
                JOptionPane.showMessageDialog(this, "Deleted Record", "Deleted Successfully", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error Deleting !!!", "Eror While Deleting", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            viewPanel("dmDeliveryDetails_Panel_DeliveryDetals");
            loaddata_deliveryDetails();
        }
    }//GEN-LAST:event_dmDeliveryDetailsDisc_btn_deleteActionPerformed

    private void dmDeliveryDetailsDisc_btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmDeliveryDetailsDisc_btn_UpdateActionPerformed
        ArrayList<String> li = new ArrayList<>();
        li.add(dmDeliveryDetailsDisc_DeliveryID.getText());
        li.add(dmDeliveryDetailsDisc_billID.getText());
        li.add(dmDeliveryDetailsDisc_custName.getText());
        li.add(dmDeliveryDetailsDisc_date.getText());
        li.add(dmDeliveryDetailsDisc_time.getText());
        li.add(dmDeliveryDetails_address.getText());
        li.add(dmDeliveryDetailsDisc_town.getText());
        li.add(dmDeliveryDetails_vehicleID.getText());
        li.add(dmDeliveryDetailsDisc_type.getText());

        updateDelivery_setValues(li);
        viewPanel("dmupdateDelivery_Panel_UpdateDelivery");

    }//GEN-LAST:event_dmDeliveryDetailsDisc_btn_UpdateActionPerformed

    private void dmupdateDelivery_btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmupdateDelivery_btn_UpdateActionPerformed
        String time = dmupdateDelivery_time.getText() + " " + dmupdateDelivery_Label_am.getSelectedItem().toString();
        try {
            String qry = "UPDATE deliveries SET deliveries.custName ='" + dmupdateDelivery_custName.getText() + "' , deliveries.dispatchDate = '" + dmupdateDelivery_Date.getText() + "' , deliveries.dispatchTime='" + time + "' , deliveries.Address = '" + dmupdateDelivery_address.getText() + "' , deliveries.Town='" + dmupdateDelivery_town.getText() + "' , deliveries.vehicleId ='" + dmupdateDelivery_vehicleID.getSelectedItem().toString() + "' WHERE deliveryID = '" + dmupdateDelivery_DeliveryID.getText() + "'";
            ShakithDBConnect.iud(qry);
            JOptionPane.showMessageDialog(this, "Update Successful", "Updated Successfully", JOptionPane.INFORMATION_MESSAGE);
            viewPanel("dmDeliveryDetails_Panel_DeliveryDetails");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Updating !!!", "Eror While Updating", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_dmupdateDelivery_btn_UpdateActionPerformed

    private void dmupdateDelivery_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmupdateDelivery_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmupdateDelivery_typeActionPerformed

    private void dmupdateDelivery_vehicleIDPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_dmupdateDelivery_vehicleIDPopupMenuWillBecomeVisible
        String id = dmupdateDelivery_vehicleID.getItemAt(dmupdateDelivery_vehicleID.getItemCount() - 1);
        dmupdateDelivery_vehicleID.removeItemAt(dmupdateDelivery_vehicleID.getItemCount() - 1);
        try {
            String qry = "SELECT delivery_vehicle.vehicleID,delivery_vehicle.make FROM delivery_vehicle WHERE delivery_vehicle.vehicleID = '" + id + "'";
            ResultSet rs = ShakithDBConnect.search(qry);
            while (rs.next()) {
                dmupdateDelivery_vehicleID.addItem(rs.getString("v"));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dmupdateDelivery_vehicleIDPopupMenuWillBecomeVisible

    private void dmupdateDelivery_townActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmupdateDelivery_townActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmupdateDelivery_townActionPerformed

    private void dmupdateDelivery_DeliveryIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmupdateDelivery_DeliveryIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmupdateDelivery_DeliveryIDActionPerformed

    private void dmupdateDelivery_billIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmupdateDelivery_billIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmupdateDelivery_billIDActionPerformed

    private void dmupdateDelivery_Label_amPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_dmupdateDelivery_Label_amPopupMenuWillBecomeVisible
        dmupdateDelivery_Label_am.removeItemAt(dmupdateDelivery_Label_am.getItemCount() - 1);
    }//GEN-LAST:event_dmupdateDelivery_Label_amPopupMenuWillBecomeVisible

    private void dmupdateDelivery_timeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmupdateDelivery_timeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmupdateDelivery_timeActionPerformed

    private void dmupdateDelivery_DateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmupdateDelivery_DateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmupdateDelivery_DateActionPerformed

    private void dmupdateDelivery_custNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmupdateDelivery_custNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmupdateDelivery_custNameActionPerformed

    private void dmVehicleDetails_searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dmVehicleDetails_searchFocusGained
        if (dmVehicleDetails_search.getText().equals("Search Here") && dmVehicleDetails_search.getForeground().equals(new Color(204, 204, 204))) {
            dmVehicleDetails_search.setText(null);
            dmVehicleDetails_search.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_dmVehicleDetails_searchFocusGained

    private void dmVehicleDetails_searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dmVehicleDetails_searchFocusLost

        if (dmVehicleDetails_search.getText().isEmpty() && dmVehicleDetails_search.getForeground().equals(new Color(0, 0, 0))) {
            dmVehicleDetails_search.setText("Search Here");
            dmVehicleDetails_search.setForeground(new Color(204, 204, 204));
        }
    }//GEN-LAST:event_dmVehicleDetails_searchFocusLost

    private void dmVehicleDetails_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetails_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmVehicleDetails_searchActionPerformed

    private void dmVehicleDetails_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetails_btnActionPerformed

        if (dmVehicleDetails_search.getText().isEmpty() || (dmVehicleDetails_search.getText().equals("Search Here") && dmVehicleDetails_search.getForeground().equals(new Color(204, 204, 204)))) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) dmVehicleDetails_table.getModel()));
            sorter.setRowFilter(RowFilter.regexFilter(""));
            dmVehicleDetails_table.setRowSorter(sorter);

        }
        if (!(dmVehicleDetails_search.getText().isEmpty() || (dmVehicleDetails_search.getText().equals("Search Here") && dmVehicleDetails_search.getForeground().equals(new Color(204, 204, 204))))) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) dmVehicleDetails_table.getModel()));
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + dmVehicleDetails_search.getText()));
            dmVehicleDetails_table.setRowSorter(sorter);
        }
    }//GEN-LAST:event_dmVehicleDetails_btnActionPerformed

    private void dmVehicleDetails_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dmVehicleDetails_tableMouseClicked
        if (evt.getClickCount() == 2) {

            DefaultTableModel dtm = (DefaultTableModel) dmVehicleDetails_table.getModel();
            vnum = dtm.getValueAt(dmVehicleDetails_table.getSelectedRow(), 0).toString();
            setDetails_vehicleDetailsDisc();
            viewPanel("dmVehicleDetailsDisc_Panel_Main");

        }
    }//GEN-LAST:event_dmVehicleDetails_tableMouseClicked

    private void dmVehicleDetailsDisc_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetailsDisc_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmVehicleDetailsDisc_idActionPerformed

    private void dmVehicleDetailsDisc_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetailsDisc_registerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmVehicleDetailsDisc_registerActionPerformed

    private void dmVehicleDetailsDisc_makeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetailsDisc_makeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmVehicleDetailsDisc_makeActionPerformed

    private void dmVehicleDetailsDisc_cateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetailsDisc_cateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmVehicleDetailsDisc_cateActionPerformed

    private void dmVehicleDetailsDisc_fuelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetailsDisc_fuelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmVehicleDetailsDisc_fuelActionPerformed

    private void dmVehicleDetailsDisc_capaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetailsDisc_capaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmVehicleDetailsDisc_capaActionPerformed

    private void dmVehicleDetailsDisc_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetailsDisc_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmVehicleDetailsDisc_dateActionPerformed

    private void dmVehicleDetailsDisc_btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetailsDisc_btnUpdateActionPerformed
        ArrayList<String> li = new ArrayList<>();
        li.add(dmVehicleDetailsDisc_id.getText());
        li.add(dmVehicleDetailsDisc_register.getText());
        li.add(dmVehicleDetailsDisc_make.getText());
        li.add(dmVehicleDetailsDisc_cate.getText());
        li.add(dmVehicleDetailsDisc_fuel.getText());
        li.add(dmVehicleDetailsDisc_capa.getText());
        li.add(dmVehicleDetailsDisc_desc.getText());
        li.add(dmVehicleDetailsDisc_date.getText());
        ImageIcon i = (ImageIcon) dmVehicleDetailsDisc_Label_img.getIcon();
        System.out.println(li.toString());
        updateVehicle_setValues(li, i, p);
        viewPanel("dmUpdateVehicle_Panel_main");
    }//GEN-LAST:event_dmVehicleDetailsDisc_btnUpdateActionPerformed

    private void dmVehicleDetailsDisc_btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmVehicleDetailsDisc_btnDeleteActionPerformed

        int x = JOptionPane.showConfirmDialog(null, "Do you really want to Delete");
        if (x == 0) {
            try {
                String qry = "DELETE FROM delivery_vehicle WHERE delivery_vehicle.vehicleID = '" + dmVehicleDetailsDisc_id.getText() + "' ";
                ShakithDBConnect.iud(qry);
                JOptionPane.showMessageDialog(this, "Deleted Record", "Deleted Successfully", JOptionPane.INFORMATION_MESSAGE);
                viewPanel("dmVehicleDetails_Panel_main");
                loadData_vehicleDetails();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error Deleting !!!", "Eror While Deleting", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_dmVehicleDetailsDisc_btnDeleteActionPerformed

    private void dmUpdateVehicle_vehicleIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmUpdateVehicle_vehicleIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmUpdateVehicle_vehicleIdActionPerformed

    private void dmUpdateVehicle_registerNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmUpdateVehicle_registerNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmUpdateVehicle_registerNumActionPerformed

    private void dmUpdateVehicle_makeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmUpdateVehicle_makeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmUpdateVehicle_makeActionPerformed

    private void dmUpdateVehicle_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmUpdateVehicle_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmUpdateVehicle_dateActionPerformed

    private void dmUpdateVehicle_capacityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmUpdateVehicle_capacityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmUpdateVehicle_capacityActionPerformed

    private void dmUpdateVehicle_btnChoose_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmUpdateVehicle_btnChoose_ActionPerformed
        try {
            JFileChooser jf = new JFileChooser("D:\\Wallpapers");
            int option = jf.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File f = jf.getSelectedFile();
                BufferedImage img = ImageIO.read(f);
                Image dimg = img.getScaledInstance(dmUpdateVehicle_Label_img.getWidth(), dmUpdateVehicle_Label_img.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                dmUpdateVehicle_Label_img.setIcon(imageIcon);
                path = f.toPath().toString().replace("\\", "\\\\");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_dmUpdateVehicle_btnChoose_ActionPerformed

    private void dmUpdateVehicle_btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmUpdateVehicle_btnUpdateActionPerformed
        path = path.replace("\\", "\\\\");
        try {
            String qry = "UPDATE delivery_vehicle SET delivery_vehicle.make ='" + dmUpdateVehicle_make.getSelectedItem().toString() + "' , delivery_vehicle.category = '" + dmUpdateVehicle_category.getSelectedItem().toString() + "' , delivery_vehicle.fuelType='" + dmUpdateVehicle_fuelType.getSelectedItem().toString() + "' , delivery_vehicle.capacity = '" + dmUpdateVehicle_capacity.getText() + "' , delivery_vehicle.description='" + dmUpdateVehicle_desc.getText() + "' , delivery_vehicle.path ='" + path + "' WHERE vehicleID = '" + dmUpdateVehicle_vehicleId.getText() + "'";
            ShakithDBConnect.iud(qry);
            JOptionPane.showMessageDialog(this, "Update Successful", "Updated Successfully", JOptionPane.INFORMATION_MESSAGE);
            viewPanel("dmVehicleDetails_Panel_main");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Updating !!!", "Eror While Updating", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_dmUpdateVehicle_btnUpdateActionPerformed

    private void ReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportsActionPerformed
        // TODO add your handling code here:
        try {
            String Report = "C:\\Users\\Chathura Harshanga\\Documents\\NetBeansProjects\\ITP\\test\\CUS\\report1.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(Report);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connn);
            JasperViewer.viewReport(jasperPrint);
        } catch (Exception e) {
            System.out.println("view.NewJFrame.ReportsActionPerformed()" + e);
        }
    }//GEN-LAST:event_ReportsActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        CusDB service = new CusDB();

        String name = Cus_search.getText();

        cus_view_table.setModel(DbUtils.resultSetToTableModel(service.searchCUS(name)));


    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        ThanujaServiceImpl th = new ThanujaServiceImpl();
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        th.generateReport(path);

        JOptionPane.showMessageDialog(rootPane, "Payments report generated");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void SuMPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuMPlaceActionPerformed
        // TODO add your handling code here:
        supDB sdb = new supDB();
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        sdb.generateReport(path);

        JOptionPane.showMessageDialog(rootPane, "Report generated");
    }//GEN-LAST:event_SuMPlaceActionPerformed

    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        // TODO add your handling code here:
        int ordrowselected = orderTable.getSelectedRow();
        String orderID;
        String itemID;
        String qty;
        String supID;
        String amount;
        orderID = orderTable.getValueAt(ordrowselected, 0).toString();
        itemID = orderTable.getValueAt(ordrowselected, 1).toString();
        qty = orderTable.getValueAt(ordrowselected, 2).toString();
        amount = orderTable.getValueAt(ordrowselected, 3).toString();
        supID = orderTable.getValueAt(ordrowselected, 4).toString();

        orderIDtxt.setText(orderID);
        Itemcombo.setSelectedItem(itemID);
        supIDcombo.setSelectedItem(supID);
        quantitytxt.setText(qty);
        amounttxt.setText(amount);
    }//GEN-LAST:event_orderTableMouseClicked

    private void orderIDtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderIDtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orderIDtxtActionPerformed

    private void DeletebtnorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeletebtnorderActionPerformed
        // TODO add your handling code here:
        int response = JOptionPane.showConfirmDialog(null, "Do you really want to delete selected order?");

        if (response == 0) {

            String id = orderIDtxt.getText();

            globalSupDB.deleteorder(id);
            globalSupDB.orderTableLoad(orderTable);
        }
    }//GEN-LAST:event_DeletebtnorderActionPerformed

    private void supIDcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supIDcomboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supIDcomboActionPerformed

    private void ItemcomboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ItemcomboMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_ItemcomboMouseClicked

    private void ItemcomboMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ItemcomboMouseExited
        // TODO add your handling code here:
        //        String g = Itemcombo.getSelectedItem().toString();
        //        ResultSet type = globalSupDB.getSupCategory(g);
        //try{
        //
        //            Vector<SupplyModel> vector = new Vector<>();
        //
        //            while(type.next()){
        //
        //                SupplyModel s1 = new SupplyModel(type.getString("supplierId"));
        //
        //                vector.addElement(s1);
        //
        //            }
        //
        //            supIDcombo.setModel(new DefaultComboBoxModel(vector));
        //
        //        }catch(SQLException ex){
        //
        //            System.out.println("ex");
        //
        //        }

    }//GEN-LAST:event_ItemcomboMouseExited

    private void placeOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderActionPerformed
        // TODO add your handling code here:

        try {

            SupplyModel s1;
            ItemSupModel i2;
            String amount = amounttxt.getText();
            String qty = quantitytxt.getText();
            s1 = (SupplyModel) supIDcombo.getSelectedItem();
            i2 = (ItemSupModel) Itemcombo.getSelectedItem();
            int sid = s1.getSupId();
            int Iid = i2.getId();

            if (amount.equals("") || qty.equals("")) {

                JOptionPane.showMessageDialog(supOrderDetails, "Enter positive values to the fields");

            } else if (Double.parseDouble(amount) == 0 || Integer.parseInt(qty) == 0) {

                JOptionPane.showMessageDialog(supOrderDetails, "Fields should not be zero...Enter a positive Value!");

            } else {

                int response = JOptionPane.showConfirmDialog(null, "Do you really want to Place the order");

                if (response == 0) {
                    //Send data to model
                    try {
                        orderModel o2 = new orderModel(Integer.parseInt(qty), Double.parseDouble(amount));

                        //Call method to access DB
                        globalSupDB.addOrder(o2, sid, Iid);

                        globalSupDB.orderTableLoad(orderTable);
                    } catch (NumberFormatException e) {

                        JOptionPane.showMessageDialog(supOrderDetails, "Enter valid inputs!");

                    }
                }

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(supOrderDetails, "Enter valid inputs!");

        }
    }//GEN-LAST:event_placeOrderActionPerformed

    private void OrderjTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderjTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OrderjTextField5ActionPerformed

    private void OrderjTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OrderjTextField5KeyReleased
        // TODO add your handling code here:

        String by = orderjComboBox3.getSelectedItem().toString();
        String key = OrderjTextField5.getText().toString();

        orderTable.setModel(DbUtils.resultSetToTableModel(globalSupDB.search_item_order(by, key)));
    }//GEN-LAST:event_OrderjTextField5KeyReleased

    private void supNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supNameActionPerformed

    private void supUpdatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supUpdatebtnActionPerformed
        // TODO add your handling code here:

        int response = JOptionPane.showConfirmDialog(null, "Do you really want to update?");
        if (response == 0) {
            String name = supName.getText();
            String email = supEmail.getText();
            String phone = supPno.getText();
            int id = Integer.parseInt(supID.getText());
            //int phoneLength = Integer.toString(phone).length();
            String address = supAddress.getText();

            supDB db = new supDB();

            if (name.equals("") || email.equals("") || phone.isEmpty() || address.equals("")) {

                JOptionPane.showMessageDialog(supAddDetails, "Fill all form fields! Enter 'None' if non-existant");

            } else if (!supplierValidator.isValidEmailAddress(email)) {

                JOptionPane.showMessageDialog(supAddDetails, "Enter valid email address!");
            } else if (phone.length() != 10) {
                JOptionPane.showMessageDialog(supAddDetails, "Enter phone number with 10 digits!");
            } else {

                try {
                    SupplyModel s1 = new SupplyModel(name, email, Integer.parseInt(phone), address);
                    s1.setSupId(id);

                    globalSupDB.updateSupplier(s1);

                    globalSupDB.supTableLoad(supTable);
                } catch (Exception e) {

                    JOptionPane.showMessageDialog(supAddDetails, "Enter valid phone number!");

                }
            }

        }
    }//GEN-LAST:event_supUpdatebtnActionPerformed

    private void supAddbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supAddbtnActionPerformed
        // TODO add your handling code here:
        try {
            String name = supName.getText();
            String email = supEmail.getText();
            String phone = supPno.getText();
            //int phoneLength = Integer.toString(phone).length();
            String address = supAddress.getText();

            supDB db = new supDB();
            if (db.checkRepeat(email) == false) {

                if (name.equals("") || email.equals("") || phone.isEmpty() || address.equals("")) {

                    JOptionPane.showMessageDialog(supAddDetails, "Fill all form fields! Enter 'None' if non-existant");

                } else if (!supplierValidator.isValidEmailAddress(email)) {

                    JOptionPane.showMessageDialog(supAddDetails, "Enter valid email address!");
                } else if (phone.length() != 10) {
                    JOptionPane.showMessageDialog(supAddDetails, "Enter phone number with 10 digits!");
                } else {

                    int response = JOptionPane.showConfirmDialog(null, "Do you really want to register this supplier?");

                    if (response == 0) {
                        //Send data to model
                        try {
                            SupplyModel s1 = new SupplyModel(name, email, Integer.parseInt(phone), address);

                            globalSupDB.addSupply(s1);

                            globalSupDB.supTableLoad(supTable);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(supAddDetails, "Enter valid phone number!");

                            //Call method to access DB
                        }
                    }
                }
            } else {

                JOptionPane.showMessageDialog(supAddDetails, "supplier is already exists");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(supAddDetails, "Enter valid phone number!");

            System.out.println(e);

        }
    }//GEN-LAST:event_supAddbtnActionPerformed

    private void supAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supAddressActionPerformed

    private void supTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supTableMouseClicked
        // TODO add your handling code here:
        int suprowselected = supTable.getSelectedRow();
        String ID;
        String name;
        String email;
        String phone;
        String address;
        ID = supTable.getValueAt(suprowselected, 0).toString();
        name = supTable.getValueAt(suprowselected, 1).toString();
        email = supTable.getValueAt(suprowselected, 2).toString();
        phone = supTable.getValueAt(suprowselected, 3).toString();
        address = supTable.getValueAt(suprowselected, 4).toString();

        supID.setText(ID);
        supName.setText(name);
        supEmail.setText(email);
        supPno.setText(phone);
        supAddress.setText(address);
    }//GEN-LAST:event_supTableMouseClicked

    private void supDeletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supDeletebtnActionPerformed
        // TODO add your handling code here:

        int response = JOptionPane.showConfirmDialog(null, "Do you really want to delete selected supplier?");

        if (response == 0) {

            String id = supID.getText();

            globalSupDB.deletesup(id);
            globalSupDB.supTableLoad(supTable);
        }

    }//GEN-LAST:event_supDeletebtnActionPerformed

    private void supjTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_supjTextField5KeyReleased
        // TODO add your handling code here:

        String by = supjComboBox3.getSelectedItem().toString();
        String key = supjTextField5.getText().toString();

        supTable.setModel(DbUtils.resultSetToTableModel(globalSupDB.search_item(by, key)));

    }//GEN-LAST:event_supjTextField5KeyReleased

    private void supDemoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supDemoActionPerformed
        // TODO add your handling code here:
        supName.setText("Motors");
        supEmail.setText("ABC@gmail.com");
        supPno.setText("0721234567");
        supAddress.setText("Temple Road,malabe");

    }//GEN-LAST:event_supDemoActionPerformed

    private void empReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empReportsActionPerformed

        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        globalEmpDB.generateReport(path);

        JOptionPane.showMessageDialog(rootPane, "Employee Report Saved!");
    }//GEN-LAST:event_empReportsActionPerformed

    private void empLeaveRequestBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empLeaveRequestBtnActionPerformed
        try {
            int empID = 5;
            int remLeave = Integer.parseInt(empRemLeavBox.getText());
            //String startDate = empstartDateBox.getText();
            //String endDate = empEndDateBox.getText();

            String startDate = ((JTextField) empLeaveStartDate.getDateEditor().getUiComponent()).getText();
            String endDate = ((JTextField) empLeaveEndDate.getDateEditor().getUiComponent()).getText();

            String reason = empReasonText.getText();
            int noOfDays = Integer.parseInt(empDaysBox.getText());

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(cal.getTime());

            //Validate empty fields
            if (startDate.equals("") || endDate.equals("") || reason.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all form fields!");
            } //Validate number of days
            else if (noOfDays == 0 || noOfDays > remLeave) {
                JOptionPane.showMessageDialog(null, "Number of days cannot be zero or exceed remaining leave amount!");
            } //Validate date format
            else if (!employeeValidator.isValidDate(startDate)) {

                JOptionPane.showMessageDialog(null, "Please use yyyy/MM/dd format to enter starting date and ending date!");
            } //Validate date format
            //            else if(!employeeValidator.isValidDate(endDate)){
            //
            //                JOptionPane.showMessageDialog(null, "Please use yyyy/MM/dd format to enter starting date and ending date!");
            //            }
            //Validate start date before end date
            //            else if (!employeeValidator.isDateGreater(startDate, endDate)) {
            //
            //                JOptionPane.showMessageDialog(null, "Starting date must be before ending date!");
            //
            //            } //Validate startDate is greater than current date
            else if (employeeValidator.isDateGreater(startDate, currentDate)) {

                JOptionPane.showMessageDialog(null, "Starting date cannot be before todays date!");

            } //Check overlap
            else if (globalEmpDB.checkLeaveRequestOverlap(empID, startDate, endDate)) {

                JOptionPane.showMessageDialog(null, "Request overlaps a previous request!");

            } else {
                EmployeeLeaveModel empLeave = new EmployeeLeaveModel(empID, startDate, endDate, noOfDays, reason);

                globalEmpDB.addEmpLeaveRequest(empLeave);

                globalEmpDB.empLeaveRequestTableLoad(empRequestTable);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Number of days must be an integer!");
        }
    }//GEN-LAST:event_empLeaveRequestBtnActionPerformed

    private void empRequestTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empRequestTableMouseClicked

        int leaveTableRow = empRequestTable.getSelectedRow();

        String startDate = empRequestTable.getValueAt(leaveTableRow, 2).toString();
        String endDate = empRequestTable.getValueAt(leaveTableRow, 3).toString();
        String nDays = empRequestTable.getValueAt(leaveTableRow, 4).toString();
        String reason = empRequestTable.getValueAt(leaveTableRow, 7).toString();

        String approved = empRequestTable.getValueAt(leaveTableRow, 5).toString();
        String rejected = empRequestTable.getValueAt(leaveTableRow, 6).toString();
        //Add modification
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        formatter.setLenient(false);
        java.util.Date date;
        try {
            date = formatter.parse(startDate);
            empLeaveStartDate.setDate(date);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            date = formatter.parse(endDate);
            empLeaveEndDate.setDate(date);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        //empEndDateBox.setText(endDate);
        empDaysBox.setText(nDays);
        empReasonText.setText(reason);
        empLeaveStatusLabel.setText("Request Status");

        if (approved.equals("true")) {

            empLeaveStatusBox.setText("  Request Approved  ");
            empLeaveStatusBox.setBackground(Color.GREEN);
            empLeaveStatusBox.setForeground(Color.WHITE);
        } else if (rejected.equals("true")) {
            empLeaveStatusBox.setText("  Request Rejected  ");
            empLeaveStatusBox.setBackground(Color.RED);
            empLeaveStatusBox.setForeground(Color.WHITE);
        } else {
            empLeaveStatusBox.setText("  Request Pending  ");
            empLeaveStatusBox.setBackground(Color.BLUE);
            empLeaveStatusBox.setForeground(Color.WHITE);
        }
    }//GEN-LAST:event_empRequestTableMouseClicked

    private void empAddressBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empAddressBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_empAddressBoxActionPerformed

    private void deleteEmpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEmpBtnActionPerformed

        int response = JOptionPane.showConfirmDialog(null, "Do you really want to delete selected employee?");

        if (response == 0) {

            String id = empIdBox.getText();

            globalEmpDB.deleteEmp(id);
            globalEmpDB.empTableLoad(empTable);
        }
    }//GEN-LAST:event_deleteEmpBtnActionPerformed

    private void registerEmpBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerEmpBtn1ActionPerformed

        //Get data from form
        try {

            EmployeeType e2;
            e2 = (EmployeeType) emptypeComboBox.getSelectedItem();

            String name = empNameBox.getText();
            String nic = empNICBox.getText();
            String address = empAddressBox.getText();
            int phone = Integer.parseInt(empPhoneBox.getText());
            String email = empEmailBox.getText();
            int type = e2.getEmpTypeId();
            int phoneLength = Integer.toString(phone).length();

            EmpDB db = new EmpDB();

            //Check for duplicate entries
            if (db.checkRepeat(nic) == false) {
                //Form field validation
                //Check empty fields
                if (name.equals("") || nic.equals("") || address.equals("") || email.equals("") || phoneLength == 0) {

                    JOptionPane.showMessageDialog(addEmpCard1, "Fill all form fields! Enter 'None' if non-existant");

                } //Validate email
                else if (!employeeValidator.isValidEmailAddress(email)) {

                    JOptionPane.showMessageDialog(addEmpCard1, "Enter valid email address!");
                } else if (phoneLength != 10) {

                    JOptionPane.showMessageDialog(addEmpCard1, "Enter phone number with 10 digits!");
                } else if (nic.length() < 10 || nic.length() > 12) {

                    JOptionPane.showMessageDialog(addEmpCard1, "NIC must contain between 10-12 characters!");
                } //If no errors
                else {
                    int response = JOptionPane.showConfirmDialog(null, "Do you really want to register this employee?");
                    if (response == 0) {
                        //Send data to model
                        EmployeeModel e1 = new EmployeeModel(name, nic, address, email, type, phone);

                        //Call method to access DB
                        db.addEmployee(e1);

                        globalEmpDB.empTableLoad(empTable);
                    }
                }
            } else {

                JOptionPane.showMessageDialog(addEmpCard1, "Employee already exists!");
            }
        } //Catch converting invalid phone numbers
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(addEmpCard1, "Enter valid phone number!");
        }
    }//GEN-LAST:event_registerEmpBtn1ActionPerformed

    private void updateEmpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEmpBtnActionPerformed

        int response = JOptionPane.showConfirmDialog(null, "Do you really want to update?");
        if (response == 0) {

            String empID = empIdBox.getText();
            String empName = empNameBox.getText();
            String empNic = empNICBox.getText();
            String empAddress = empAddressBox.getText();
            int phone = Integer.parseInt(empPhoneBox.getText());
            String empEmail = empEmailBox.getText();
            int empType = emptypeComboBox.getSelectedIndex();
            int phoneLength = Integer.toString(phone).length();

            if (empName.equals("") || empNic.equals("") || empAddress.equals("") || empEmail.equals("") || phoneLength == 0) {

                JOptionPane.showMessageDialog(addEmpCard1, "Fill all form fields! Enter 'None' if non-existant");

            } //Validate email
            else if (!employeeValidator.isValidEmailAddress(empEmail)) {

                JOptionPane.showMessageDialog(addEmpCard1, "Enter valid email address!");
            } else if (phoneLength != 10) {

                JOptionPane.showMessageDialog(addEmpCard1, "Enter phone number with 10 digits!");
            } else if (empNic.length() < 10 || empNic.length() > 12) {

                JOptionPane.showMessageDialog(addEmpCard1, "NIC must contain between 10-12 characters!");
            } else {
                //Send data to model
                EmployeeModel e1 = new EmployeeModel(empName, empNic, empAddress, empEmail, empType, phone);
                e1.setEmpId(Integer.parseInt(empID));
                //Call method to access DB

                globalEmpDB.updateEmployee(e1);

                globalEmpDB.empTableLoad(empTable);
            }

        }
    }//GEN-LAST:event_updateEmpBtnActionPerformed

    private void empTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empTableMouseClicked

        //Access emp table row
        int empTableRow = empTable.getSelectedRow();

        String empID = empTable.getValueAt(empTableRow, 0).toString();
        String empName = empTable.getValueAt(empTableRow, 1).toString();
        String empNic = empTable.getValueAt(empTableRow, 2).toString();
        String empAddress = empTable.getValueAt(empTableRow, 3).toString();
        String empPhone = empTable.getValueAt(empTableRow, 4).toString();;
        String empEmail = empTable.getValueAt(empTableRow, 5).toString();
        String empType = empTable.getValueAt(empTableRow, 6).toString();

        int empTypeIndex = Integer.parseInt(empType);

        empIdBox.setText(empID);
        empNameBox.setText(empName);
        empNICBox.setText(empNic);
        empAddressBox.setText(empAddress);
        empPhoneBox.setText(empPhone);
        empEmailBox.setText(empEmail);
        emptypeComboBox.setSelectedIndex(empTypeIndex);
    }//GEN-LAST:event_empTableMouseClicked

    private void empIdBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empIdBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_empIdBoxActionPerformed

    private void EmpSearchBarFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmpSearchBarFieldMouseClicked

        //Add modification
        EmpSearchBarField.setText("");

    }//GEN-LAST:event_EmpSearchBarFieldMouseClicked

    private void EmpSearchBarFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpSearchBarFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmpSearchBarFieldActionPerformed

    private void EmpSearchBarFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EmpSearchBarFieldKeyReleased
        //Add modification
        String empSearchTerm = EmpSearchBarField.getText();

        String query = "SELECT * FROM Employee WHERE empName LIKE '%" + empSearchTerm + "%'";

        try {
            Connection conn = DBConnect.DBConn();
            pst = conn.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs = pst.executeQuery();
            empTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_EmpSearchBarFieldKeyReleased

    private void smreportbtnsalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smreportbtnsalesActionPerformed
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        sms1.viewreportsales(path);

        JOptionPane.showMessageDialog(rootPane, "Report Generated");

    }//GEN-LAST:event_smreportbtnsalesActionPerformed

    private void smreportbtnbillsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smreportbtnbillsActionPerformed
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        sms1.viewreportbill(path);

        JOptionPane.showMessageDialog(rootPane, "Report Generated");

    }//GEN-LAST:event_smreportbtnbillsActionPerformed

    private void smreportbtnsalerecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smreportbtnsalerecActionPerformed
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        sms1.viewreportsalesrec(path);

        JOptionPane.showMessageDialog(rootPane, "Report Generated");
    }//GEN-LAST:event_smreportbtnsalerecActionPerformed

    private void smtablebillsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smtablebillsMouseMoved
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        smtablebills.setModel(DbUtils.resultSetToTableModel(sms1.getbills()));
    }//GEN-LAST:event_smtablebillsMouseMoved

    private void smtablesalesMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smtablesalesMouseMoved
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        smtablesales.setModel(DbUtils.resultSetToTableModel(sms1.getsales()));
    }//GEN-LAST:event_smtablesalesMouseMoved

    private void dm_vehicleReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dm_vehicleReportActionPerformed
        // TODO add your handling code here:

        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        generateReport(path);

        JOptionPane.showMessageDialog(rootPane, "Vehicle Report Generated");
    }//GEN-LAST:event_dm_vehicleReportActionPerformed

    private void dm_deliveryReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dm_deliveryReportActionPerformed
        // TODO add your handling code here:

        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        generateDeliveryReport(path);

        JOptionPane.showMessageDialog(rootPane, "Delivery Report Generated");
    }//GEN-LAST:event_dm_deliveryReportActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddCustomers;
    private javax.swing.JPanel CMjPanel10;
    private javax.swing.JTextField Cus_search;
    private javax.swing.JPanel DMjPanel9;
    private javax.swing.JButton Deletebtnorder;
    private javax.swing.JPanel EMjPanel4;
    private javax.swing.JTextField EmpSearchBarField;
    private javax.swing.JPanel FMjPanel11;
    private javax.swing.JLabel ID_Val;
    private javax.swing.JLabel ID_cus;
    private javax.swing.JLabel IM_ADD_txt;
    private javax.swing.JLabel IM_A_Bprice_error_lbl;
    private javax.swing.JLabel IM_A_Sprice_error_lbl;
    private javax.swing.JLabel IM_A_brand_error_lbl;
    private javax.swing.JLabel IM_A_name_error_lbl;
    private javax.swing.JLabel IM_A_qty_error_lbl;
    private javax.swing.JLabel IM_A_success;
    private javax.swing.JTable IM_Atable;
    private javax.swing.JLabel IM_ID_lbl;
    private javax.swing.JLabel IM_SEARCH_txt;
    private javax.swing.JComboBox IM_Sby;
    private javax.swing.JTable IM_Stable;
    private javax.swing.JLabel IM_UPDATE_txt;
    private javax.swing.JLabel IM_U_Bprice_error_lbl;
    private javax.swing.JLabel IM_U_Sprice_error_lbl;
    private javax.swing.JLabel IM_U_brand_error_lbl;
    private javax.swing.JLabel IM_U_delete;
    private javax.swing.JLabel IM_U_name_error_lbl;
    private javax.swing.JLabel IM_U_qty_error_lbl;
    private javax.swing.JLabel IM_U_update;
    private javax.swing.JTable IM_Utable;
    private javax.swing.JButton IM_add;
    private javax.swing.JButton IM_add_btn;
    private javax.swing.JLabel IM_bprice;
    private javax.swing.JLabel IM_bprice_lbl;
    private javax.swing.JButton IM_delete_btn;
    private javax.swing.JTextField IM_key;
    private javax.swing.JLabel IM_name;
    private javax.swing.JLabel IM_name_lbl;
    private javax.swing.JLabel IM_qty;
    private javax.swing.JLabel IM_qty_lbl;
    private javax.swing.JPanel IM_report;
    private javax.swing.JButton IM_search;
    private javax.swing.JButton IM_search1;
    private javax.swing.JButton IM_search_btn;
    private javax.swing.JLabel IM_serch_by;
    private javax.swing.JLabel IM_sprice;
    private javax.swing.JLabel IM_sprice_lbl;
    private javax.swing.JLabel IM_type;
    private javax.swing.JLabel IM_type_lbl;
    private javax.swing.JButton IM_update_btn;
    private javax.swing.JButton IM_update_delete;
    private javax.swing.JPanel IMadd;
    private javax.swing.JPanel IMsearch;
    private javax.swing.JPanel IMupdate;
    private javax.persistence.EntityManager ITPPUEntityManager;
    private javax.swing.JTextField I_Bprice;
    private javax.swing.JLabel I_ID;
    private javax.swing.JTextField I_Sprice;
    private javax.swing.JTextField I_UBprice;
    private javax.swing.JTextField I_USprice;
    private javax.swing.JTextField I_Uname;
    private javax.swing.JTextField I_Uqty;
    private javax.swing.JTextField I_Utype;
    private javax.swing.JTextField I_name;
    private javax.swing.JTextField I_qty;
    private javax.swing.JTextField I_type;
    private javax.swing.JComboBox Itemcombo;
    private javax.swing.JButton ManageCustomers;
    private javax.swing.JLabel OrderIdlabel;
    private javax.swing.JTextField OrderjTextField5;
    private javax.swing.JScrollPane Ordrtble;
    private javax.swing.JLabel PMjLabel6;
    private javax.swing.JPanel PMjPanel7;
    private javax.swing.JButton Reports;
    private javax.swing.JPanel SaMjPanel8;
    private javax.swing.JButton SuMAdd;
    private javax.swing.JButton SuMOrder;
    private javax.swing.JButton SuMPlace;
    private javax.swing.JPanel addCatergoryPanel;
    private javax.swing.JPanel addEmpCard1;
    private javax.swing.JButton addEmpCatNavBtn;
    private javax.swing.JLabel addEmpTitleTxt;
    private javax.swing.JButton addEmpTypeBtn;
    private javax.swing.JLabel address_cus;
    private javax.swing.JLabel address_cus_ad;
    private javax.swing.JLabel address_val;
    private javax.swing.JLabel address_val_U2;
    private javax.swing.JTextField adress_cus_u;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JTextField amounttxt;
    private javax.swing.JPanel attendanceEmpCard;
    private javax.swing.JButton attendanceEmpNavBtn;
    private java.util.List<model.Billpayment> billpaymentList;
    private javax.persistence.Query billpaymentQuery;
    private java.util.List<model.Billtype> billtypeList;
    private javax.persistence.Query billtypeQuery;
    private javax.swing.JButton btnGenerateBills;
    private javax.swing.JButton btnHandleReturns;
    private javax.swing.JButton btnNxtGenerateBill;
    private javax.swing.JButton btnRegisterCustomers;
    private javax.swing.JButton btnViewReports;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancel_cus;
    private javax.swing.JPanel cmAddCus;
    private javax.swing.JPanel cmUDCus;
    private javax.swing.JTextField cusAddAdd;
    private javax.swing.JTextField cusEmailAdd;
    private javax.swing.JTextField cusNameAdd;
    private javax.swing.JTextField cusPhoneAdd;
    private javax.swing.JScrollPane cus_view_scroll;
    private javax.swing.JTable cus_view_table;
    private javax.swing.JTextField custp;
    private javax.swing.JComboBox<String> custpaymentmethod;
    private javax.swing.JLabel datedmUpdateVehicle_Label_date;
    private javax.swing.JButton deleteEmpBtn;
    private javax.swing.JButton deleteEmpTypeBtn;
    private javax.swing.JButton delete_cus_U;
    private javax.swing.JTextField dmAddDelivery_DeliveryID;
    private javax.swing.JComboBox<String> dmAddDelivery_Driver;
    private javax.swing.JLabel dmAddDelivery_LabelName;
    private javax.swing.JLabel dmAddDelivery_Label_Billid;
    private javax.swing.JLabel dmAddDelivery_Label_DeliveryID;
    private javax.swing.JLabel dmAddDelivery_Label_Title;
    private javax.swing.JLabel dmAddDelivery_Label_Title1;
    private javax.swing.JLabel dmAddDelivery_Label_address;
    private javax.swing.JLabel dmAddDelivery_Label_custName;
    private javax.swing.JLabel dmAddDelivery_Label_date;
    private javax.swing.JLabel dmAddDelivery_Label_driver;
    private javax.swing.JLabel dmAddDelivery_Label_time;
    private javax.swing.JLabel dmAddDelivery_Label_town;
    private javax.swing.JLabel dmAddDelivery_Label_type;
    private javax.swing.JLabel dmAddDelivery_Label_vehicle;
    private javax.swing.JTextField dmAddDelivery_Name;
    private javax.swing.JPanel dmAddDelivery_Panel_AddDelivery;
    private javax.swing.JPanel dmAddDelivery_Panel_DeliveryInfo;
    private javax.swing.JPanel dmAddDelivery_Panel_LocationInfo;
    private javax.swing.JPanel dmAddDelivery_Panel_VehicleInfp;
    private javax.swing.JComboBox<String> dmAddDelivery_VehicleId;
    private javax.swing.JTextArea dmAddDelivery_address;
    private javax.swing.JComboBox<String> dmAddDelivery_billID;
    private javax.swing.JButton dmAddDelivery_btnReset;
    private javax.swing.JButton dmAddDelivery_btn_AddDelivery;
    private javax.swing.JTextField dmAddDelivery_custName;
    private com.toedter.calendar.JDateChooser dmAddDelivery_date;
    private javax.swing.JTextField dmAddDelivery_time;
    private javax.swing.JTextField dmAddDelivery_town;
    private javax.swing.JTextField dmAddDelivery_type;
    private javax.swing.JLabel dmAddVehicle_Label_Resgister;
    private javax.swing.JLabel dmAddVehicle_Label_VehicleID;
    private javax.swing.JLabel dmAddVehicle_Label_capacity;
    private javax.swing.JLabel dmAddVehicle_Label_category;
    private javax.swing.JLabel dmAddVehicle_Label_date;
    private javax.swing.JLabel dmAddVehicle_Label_desc;
    private javax.swing.JLabel dmAddVehicle_Label_fuelType;
    private javax.swing.JLabel dmAddVehicle_Label_img;
    private javax.swing.JLabel dmAddVehicle_Label_kg;
    private javax.swing.JLabel dmAddVehicle_Label_make;
    private javax.swing.JPanel dmAddVehicle_Panel_AddVehicle;
    private javax.swing.JPanel dmAddVehicle_Panel_vehicleImg;
    private javax.swing.JPanel dmAddVehicle_Panel_vehicleInfo;
    private javax.swing.JButton dmAddVehicle_btnAddVehicle;
    private javax.swing.JButton dmAddVehicle_btnChooseImg;
    private javax.swing.JButton dmAddVehicle_btnReset;
    private javax.swing.JTextField dmAddVehicle_capacity;
    private javax.swing.JComboBox<String> dmAddVehicle_category;
    private javax.swing.JTextField dmAddVehicle_date;
    private javax.swing.JTextArea dmAddVehicle_desc;
    private javax.swing.JComboBox<String> dmAddVehicle_fuelType;
    private javax.swing.JComboBox<String> dmAddVehicle_make;
    private javax.swing.JTextField dmAddVehicle_registerNum;
    private javax.swing.JTextField dmAddVehicle_vehicleId;
    private javax.swing.JTextField dmDeliveryDetailsDisc_DeliveryID;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_address;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_billID;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_custName;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_date;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_deliveryID;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_time;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_title;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_town;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_type;
    private javax.swing.JLabel dmDeliveryDetailsDisc_Label_vehicleID;
    private javax.swing.JPanel dmDeliveryDetailsDisc_Panel_DeliveryDetals;
    private javax.swing.JPanel dmDeliveryDetailsDisc_Panel_DeliveryInfo;
    private javax.swing.JPanel dmDeliveryDetailsDisc_Panel_location;
    private javax.swing.JPanel dmDeliveryDetailsDisc_Panel_vehicleINFO;
    private javax.swing.JTextField dmDeliveryDetailsDisc_billID;
    private javax.swing.JButton dmDeliveryDetailsDisc_btn_Update;
    private javax.swing.JButton dmDeliveryDetailsDisc_btn_delete;
    private javax.swing.JTextField dmDeliveryDetailsDisc_custName;
    private javax.swing.JTextField dmDeliveryDetailsDisc_date;
    private javax.swing.JTextField dmDeliveryDetailsDisc_time;
    private javax.swing.JTextField dmDeliveryDetailsDisc_town;
    private javax.swing.JTextField dmDeliveryDetailsDisc_type;
    private javax.swing.JLabel dmDeliveryDetails_Label_Title;
    private javax.swing.JPanel dmDeliveryDetails_Panel_DeliveryDetails;
    private javax.swing.JTextField dmDeliveryDetails_Search;
    private javax.swing.JButton dmDeliveryDetails_Searchbtn;
    private javax.swing.JTextArea dmDeliveryDetails_address;
    private javax.swing.JTable dmDeliveryDetails_table;
    private javax.swing.JTextField dmDeliveryDetails_vehicleID;
    private javax.swing.JLabel dmUpdateVehicle_Label_capa;
    private javax.swing.JLabel dmUpdateVehicle_Label_cate;
    private javax.swing.JLabel dmUpdateVehicle_Label_desc;
    private javax.swing.JLabel dmUpdateVehicle_Label_fuel;
    private javax.swing.JLabel dmUpdateVehicle_Label_id;
    private javax.swing.JLabel dmUpdateVehicle_Label_img;
    private javax.swing.JLabel dmUpdateVehicle_Label_make;
    private javax.swing.JLabel dmUpdateVehicle_Label_num;
    private javax.swing.JLabel dmUpdateVehicle_Label_title;
    private javax.swing.JPanel dmUpdateVehicle_Panel_img;
    private javax.swing.JPanel dmUpdateVehicle_Panel_info;
    private javax.swing.JPanel dmUpdateVehicle_Panel_main;
    private javax.swing.JButton dmUpdateVehicle_btnChoose_;
    private javax.swing.JButton dmUpdateVehicle_btnUpdate;
    private javax.swing.JTextField dmUpdateVehicle_capacity;
    private javax.swing.JComboBox<String> dmUpdateVehicle_category;
    private javax.swing.JTextField dmUpdateVehicle_date;
    private javax.swing.JTextArea dmUpdateVehicle_desc;
    private javax.swing.JComboBox<String> dmUpdateVehicle_fuelType;
    private javax.swing.JLabel dmUpdateVehicle_kg;
    private javax.swing.JComboBox<String> dmUpdateVehicle_make;
    private javax.swing.JTextField dmUpdateVehicle_registerNum;
    private javax.swing.JTextField dmUpdateVehicle_vehicleId;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_capa;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_cat;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_desc;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_fuel;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_id;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_img;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_kg;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_make;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_num;
    private javax.swing.JLabel dmVehicleDetailsDisc_Label_title;
    private javax.swing.JLabel dmVehicleDetailsDisc_Labeldate;
    private javax.swing.JPanel dmVehicleDetailsDisc_Panel_Main;
    private javax.swing.JButton dmVehicleDetailsDisc_btnDelete;
    private javax.swing.JButton dmVehicleDetailsDisc_btnUpdate;
    private javax.swing.JTextField dmVehicleDetailsDisc_capa;
    private javax.swing.JTextField dmVehicleDetailsDisc_cate;
    private javax.swing.JTextField dmVehicleDetailsDisc_date;
    private javax.swing.JTextArea dmVehicleDetailsDisc_desc;
    private javax.swing.JTextField dmVehicleDetailsDisc_fuel;
    private javax.swing.JTextField dmVehicleDetailsDisc_id;
    private javax.swing.JTextField dmVehicleDetailsDisc_make;
    private javax.swing.JTextField dmVehicleDetailsDisc_register;
    private javax.swing.JLabel dmVehicleDetails_Label_title;
    private javax.swing.JPanel dmVehicleDetails_Panel_main;
    private javax.swing.JButton dmVehicleDetails_btn;
    private javax.swing.JScrollPane dmVehicleDetails_scroll;
    private javax.swing.JTextField dmVehicleDetails_search;
    private javax.swing.JTable dmVehicleDetails_table;
    private javax.swing.JButton dm_deliveryReport;
    private javax.swing.JPanel dm_report;
    private javax.swing.JLabel dm_reportTitle;
    private javax.swing.JButton dm_vehicleReport;
    private javax.swing.JButton dmjButton1;
    private javax.swing.JButton dmjButton2;
    private javax.swing.JButton dmjButton3;
    private javax.swing.JButton dmjButton4;
    private javax.swing.JButton dmjButton5;
    private javax.swing.JLabel dmjLabel1;
    private javax.swing.JTextField dmupdateDelivery_Date;
    private javax.swing.JTextField dmupdateDelivery_DeliveryID;
    private javax.swing.JLabel dmupdateDelivery_Label_BillID;
    private javax.swing.JLabel dmupdateDelivery_Label_VehicleID;
    private javax.swing.JLabel dmupdateDelivery_Label_address;
    private javax.swing.JComboBox<String> dmupdateDelivery_Label_am;
    private javax.swing.JLabel dmupdateDelivery_Label_custName;
    private javax.swing.JLabel dmupdateDelivery_Label_date;
    private javax.swing.JLabel dmupdateDelivery_Label_deliveryID;
    private javax.swing.JLabel dmupdateDelivery_Label_time;
    private javax.swing.JLabel dmupdateDelivery_Label_title;
    private javax.swing.JLabel dmupdateDelivery_Label_town;
    private javax.swing.JLabel dmupdateDelivery_Label_type;
    private javax.swing.JPanel dmupdateDelivery_Panel_DeliveryInfo;
    private javax.swing.JPanel dmupdateDelivery_Panel_Location;
    private javax.swing.JPanel dmupdateDelivery_Panel_UpdateDelivery;
    private javax.swing.JPanel dmupdateDelivery_Panel_Vehicle;
    private javax.swing.JTextArea dmupdateDelivery_address;
    private javax.swing.JTextField dmupdateDelivery_billID;
    private javax.swing.JButton dmupdateDelivery_btn_Update;
    private javax.swing.JTextField dmupdateDelivery_custName;
    private javax.swing.JTextField dmupdateDelivery_time;
    private javax.swing.JTextField dmupdateDelivery_town;
    private javax.swing.JTextField dmupdateDelivery_type;
    private javax.swing.JComboBox<String> dmupdateDelivery_vehicleID;
    private javax.swing.JLabel emIDLabel;
    private javax.swing.JTextField emailBoxCus;
    private javax.swing.JLabel email_cus_U;
    private javax.swing.JLabel email_cus_ad;
    private javax.swing.JLabel email_val;
    private javax.swing.JLabel email_val_U3;
    private javax.swing.JLabel empAddTypeLabel;
    private javax.swing.JTextField empAddressBox;
    private javax.swing.JLabel empAddressLabel;
    private javax.swing.JButton empApproveRequestBtn_mgmt;
    private javax.swing.JTable empAttendanceTable;
    private javax.swing.JTable empCatergoryTable;
    private javax.swing.JLabel empDateLabel;
    private javax.swing.JTextField empDaysBox;
    private javax.swing.JTextField empDaysBox_mgmt;
    private javax.swing.JLabel empDaysLabel;
    private javax.swing.JLabel empDaysLabel_mgmt;
    private javax.swing.JTextField empEmailBox;
    private javax.swing.JLabel empEmailLabel;
    private javax.swing.JTextField empEndDateBox_mgmt;
    private javax.swing.JTextField empEndDateBox_mgmt1;
    private javax.swing.JLabel empEndDateLabel;
    private javax.swing.JLabel empEndDateLabel_mgmt;
    private javax.swing.JLabel empEndDateLabel_mgmt1;
    private javax.swing.JTextField empID_mgmt1;
    private javax.swing.JTextField empIdBox;
    private javax.swing.JTextField empLeaveBox;
    private javax.swing.JPanel empLeaveCard;
    private com.toedter.calendar.JDateChooser empLeaveEndDate;
    private javax.swing.JLabel empLeaveLabel;
    private javax.swing.JButton empLeaveMgmtNavBtn;
    private javax.swing.JPanel empLeaveMgmtPanel;
    private javax.swing.JLabel empLeaveReqIDLabel_mgmt;
    private javax.swing.JTextField empLeaveReqID_mgmt;
    private javax.swing.JButton empLeaveRequestBtn;
    private javax.swing.JTable empLeaveRequestTable;
    private com.toedter.calendar.JDateChooser empLeaveStartDate;
    private javax.swing.JTextField empLeaveStatusBox;
    private javax.swing.JLabel empLeaveStatusLabel;
    private javax.swing.JTextField empNICBox;
    private javax.swing.JTextField empNameBox;
    private javax.swing.JLabel empNameLabel;
    private javax.swing.JLabel empNicLabel;
    private javax.swing.JTextField empPhoneBox;
    private javax.swing.JLabel empPhoneLabel;
    private javax.swing.JLabel empReasonLabel;
    private javax.swing.JLabel empReasonLabel_mgmt;
    private javax.swing.JTextArea empReasonText;
    private javax.swing.JTextArea empReasonText_mgmt;
    private javax.swing.JButton empRejectRequestBtn_mgmt;
    private javax.swing.JTextField empRemLeavBox;
    private javax.swing.JLabel empRemLeaveLabel;
    private javax.swing.JButton empReports;
    private javax.swing.JTable empRequestTable;
    private javax.swing.JTextField empSalaryBox;
    private javax.swing.JLabel empSalaryLabel;
    private javax.swing.JButton empSignInBtn;
    private javax.swing.JButton empSignOutBtn;
    private javax.swing.JTable empTable;
    private javax.swing.JTextField empTypeBox1;
    private javax.swing.JTextField empTypeIdBox;
    private javax.swing.JLabel empTypeIdLabel;
    private javax.swing.JLabel empTypeLabel;
    private java.util.List<model.Employee> employeeList;
    private javax.persistence.Query employeeQuery;
    private javax.swing.JTextField empstartDateBox_mgmt;
    private javax.swing.JLabel empstartDateLabel;
    private javax.swing.JLabel empstartDateLabel_mgmt;
    private javax.swing.JLabel empstartDateLabel_mgmt1;
    private javax.swing.JComboBox<String> emptypeComboBox;
    private javax.swing.JLabel head_add_cus;
    private javax.swing.JLabel itemIdLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel labCusID;
    private javax.swing.JButton leaveEmpNavBtn;
    private javax.swing.JLabel manage_cus;
    private javax.swing.JLabel message;
    private javax.swing.JLabel name_cus;
    private javax.swing.JLabel name_cus_t;
    private javax.swing.JTextField name_cus_u;
    private javax.swing.JLabel name_val;
    private javax.swing.JLabel name_val_U;
    private javax.swing.JTextField orderIDtxt;
    private javax.swing.JTable orderTable;
    private javax.swing.JComboBox orderjComboBox3;
    private javax.swing.JLabel orderjLabel9;
    private javax.swing.JTextField phoneBoxCus;
    private javax.swing.JLabel phone_cus;
    private javax.swing.JLabel phone_cus_u;
    private javax.swing.JLabel phone_val;
    private javax.swing.JLabel phone_val_U1;
    private javax.swing.JButton placeOrder;
    private javax.swing.JPanel pmInsertBillPayments;
    private javax.swing.JPanel pmSearchBillPayments;
    private javax.swing.JLabel qtyLabel;
    private javax.swing.JTextField quantitytxt;
    private javax.swing.JButton regEmpNavBtn;
    private javax.swing.JButton registerEmpBtn1;
    private javax.swing.JButton reset_cus_add;
    private javax.swing.JLabel smCustIdStatus;
    private javax.swing.JPanel smGenerateBills;
    private javax.swing.JPanel smGenerateBills2;
    private javax.swing.JPanel smRegisterCustomers;
    private javax.swing.JPanel smReturnHandle;
    private javax.swing.JPanel smViewReports;
    private javax.swing.JTable smbilltable;
    private javax.swing.JButton smbtnadditem;
    private javax.swing.JButton smbtndeleteitem;
    private javax.swing.JButton smbtndeleteitemreturns;
    private javax.swing.JButton smbtnsearchbillid;
    private javax.swing.JTextField smcashamtpaid;
    private javax.swing.JTextField smchangedis;
    private javax.swing.JTextField smdisref;
    private javax.swing.JLabel smdisrefstatus;
    private javax.swing.JLabel smenterbillid;
    private javax.swing.JTextField smitemid;
    private javax.swing.JTextField smitemqty;
    private javax.swing.JLabel smjLabel10;
    private javax.swing.JLabel smjLabel4;
    private javax.swing.JLabel smjLabel5;
    private javax.swing.JLabel smjLabel6;
    private javax.swing.JLabel smjLabel7;
    private javax.swing.JLabel smjLabel8;
    private javax.swing.JLabel smjLabel9;
    private javax.swing.JLabel smjLabelch;
    private javax.swing.JLabel smqtystatus;
    private javax.swing.JButton smreportbtnbills;
    private javax.swing.JButton smreportbtnsalerec;
    private javax.swing.JButton smreportbtnsales;
    private javax.swing.JButton smreset;
    private javax.swing.JLabel smsalesidreturns;
    private javax.swing.JTextField smsalesidreturnsbox;
    private javax.swing.JLabel smshowitemid;
    private javax.swing.JLabel smshowitemname;
    private javax.swing.JTable smtablebills;
    private javax.swing.JTable smtablesales;
    private javax.swing.JTextField smtotbillamt;
    private javax.swing.JTextField smtxtfieldenterbillid;
    private javax.swing.JTextField smtxtitemid;
    private javax.swing.JTextField smtxtitemqty;
    private javax.swing.JTable smviewreturnstable;
    private javax.swing.JButton submit_cuss_add;
    private javax.swing.JPanel sumPanel16;
    private javax.swing.JPanel supAddDetails;
    private javax.swing.JButton supAddbtn;
    private javax.swing.JTextField supAddress;
    private javax.swing.JLabel supAddresslabel;
    private javax.swing.JButton supDeletebtn;
    private javax.swing.JButton supDemo;
    private javax.swing.JTextField supEmail;
    private javax.swing.JLabel supEmaillabel;
    private javax.swing.JTextField supID;
    private javax.swing.JComboBox supIDcombo;
    private javax.swing.JLabel supIdlabel;
    private javax.swing.JTextField supName;
    private javax.swing.JLabel supNamelabel;
    private javax.swing.JLabel supNolabel;
    private javax.swing.JPanel supOrderDetails;
    private javax.swing.JTextField supPno;
    private javax.swing.JTable supTable;
    private javax.swing.JScrollPane supTble;
    private javax.swing.JButton supUpdatebtn;
    private javax.swing.JComboBox supjComboBox3;
    private javax.swing.JLabel supjLabel9;
    private javax.swing.JTextField supjTextField5;
    private javax.swing.JLabel suppIdlabel;
    private javax.swing.JButton updateEmpBtn;
    private javax.swing.JButton updateEmpTypeBtn;
    private javax.swing.JButton update_cus_u;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private void loadId() {
        Date d = new Date();
        dmAddDelivery_date.setDate(d);
        try {
            String qry = "SELECT delivery_vehicle.vehicleID FROM delivery_vehicle";
            ResultSet rs = ShakithDBConnect.search(qry);
            dmAddDelivery_VehicleId.removeAllItems();
            dmAddDelivery_VehicleId.addItem("Select...");
            while (rs.next()) {
                dmAddDelivery_VehicleId.addItem(rs.getString("vehicleID"));
            }
            qry = "SELECT employee.empId FROM employee";
            rs = ShakithDBConnect.search(qry);
            dmAddDelivery_Driver.removeAllItems();
            dmAddDelivery_Driver.addItem("Select...");
            while (rs.next()) {
                dmAddDelivery_Driver.addItem(rs.getString("empId"));
            }

            qry = "SELECT bill.billId FROM bill";
            rs = ShakithDBConnect.search(qry);
            dmAddDelivery_billID.removeAllItems();
            dmAddDelivery_billID.addItem("Select...");
            while (rs.next()) {
                dmAddDelivery_billID.addItem(rs.getString("billId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void incrementID() {
        String id = "DI-";
        try {
            String qry = "SELECT COUNT(deliveries.vehicleID) as count FROM deliveries";
            ResultSet rs = ShakithDBConnect.search(qry);
            rs.next();
            if (rs.getInt("count") == 0) {
                id = id + "001";
            } else {
                int i = rs.getInt("count");
                i++;
                id = id + "00" + i;
            }
            dmAddDelivery_DeliveryID.setText(id);
            this.id = id;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        incrementID();
        dmAddDelivery_VehicleId.addItem("Select...");
        dmAddDelivery_VehicleId.setSelectedIndex(dmAddDelivery_VehicleId.getItemCount() - 1);
        dmAddDelivery_billID.addItem("Select...");
        dmAddDelivery_billID.setSelectedIndex(dmAddDelivery_billID.getItemCount() - 1);
        dmAddDelivery_Driver.addItem("Select...");
        dmAddDelivery_Driver.setSelectedIndex(dmAddDelivery_Driver.getItemCount() - 1);
        dmAddDelivery_custName.setText(null);
        Date d = new Date();
        dmAddDelivery_date.setDate(d);
        dmAddDelivery_time.setText(null);
        dmAddDelivery_address.setText(null);
        dmAddDelivery_town.setText(null);
        dmAddDelivery_Name.setText(null);
        dmAddDelivery_type.setText(null);
    }

    public boolean validateInput() {
        return !(dmAddDelivery_custName.getText().isEmpty() || dmAddDelivery_billID.getSelectedItem().toString().equals("Select...") || dmAddDelivery_time.getText().isEmpty() || dmAddDelivery_address.getText().isEmpty());

    }

    private void incrementID_addVehicle() {
        String id = "VD-";
        try {
            String qry = "SELECT COUNT(delivery_vehicle.vehicleID) as count FROM delivery_vehicle";
            ResultSet rs = ShakithDBConnect.search(qry);
            rs.next();
            if (rs.getInt("count") == 0) {
                id = id + "001";
            } else {
                int i = rs.getInt("count");
                i++;
                id = id + "00" + i;
            }
            dmAddVehicle_vehicleId.setText(id);
            this.id = id;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDate_addVehicle() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(d);
        dmAddVehicle_date.setText(date);
    }

    private void reset_addVehicle() {
        incrementID_addVehicle();
        setDate_addVehicle();
        dmAddVehicle_make.setSelectedIndex(0);
        dmAddVehicle_category.setSelectedIndex(0);
        dmAddVehicle_fuelType.setSelectedIndex(0);
        dmAddVehicle_registerNum.setText(null);
        dmAddVehicle_capacity.setText(null);
        dmAddVehicle_desc.setText(null);
        path = null;
        dmAddVehicle_Label_img.setIcon(null);
    }

    public boolean validateInput_addVehicle() {
        return !(dmAddVehicle_registerNum.getText().isEmpty() && dmAddVehicle_make.getSelectedIndex() == 0 && dmAddVehicle_category.getSelectedIndex() == 0 && dmAddVehicle_fuelType.getSelectedIndex() == 0 && dmAddVehicle_capacity.getText().isEmpty() && dmAddVehicle_desc.getText().isEmpty() && path == null);

    }

    private void loaddata_deliveryDetails() {
        try {
            DefaultTableModel dtm = (DefaultTableModel) dmDeliveryDetails_table.getModel();
            String qur = "SELECT deliveries.deliveryID,deliveries.Town,deliveries.dispatchDate,deliveries.dispatchTime,deliveries.status FROM deliveries;";
            ResultSet rs = ShakithDBConnect.search(qur);
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("deliveries.deliveryID"));
                v.add(rs.getString("deliveries.Town"));
                v.add(rs.getString("deliveries.dispatchDate") + " " + rs.getString("deliveries.dispatchTime"));
                if (rs.getString("deliveries.status").equals("Pending...")) {
                    v.add(false);
                } else {
                    v.add(true);
                }
                dtm.addRow(v);

            }
            for (int i = 0; i < dmDeliveryDetails_table.getColumnCount() - 1; i++) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(dmDeliveryDetails_Label_Title.CENTER);
                dmDeliveryDetails_table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDetails_DeliveryDetails() {
        try {
            String qry = "SELECT * FROM deliveries WHERE deliveryID='" + vnum + "'";
            ResultSet rs = ShakithDBConnect.search(qry);
            rs.first();
            dmDeliveryDetailsDisc_DeliveryID.setText(rs.getString("deliveryID"));
            dmDeliveryDetailsDisc_billID.setText(rs.getString("billId"));
            dmDeliveryDetailsDisc_custName.setText(rs.getString("custName"));
            dmDeliveryDetailsDisc_date.setText(rs.getString("dispatchDate"));
            dmDeliveryDetailsDisc_time.setText(rs.getString("dispatchTime"));
            dmDeliveryDetailsDisc_town.setText(rs.getString("Town"));
            dmDeliveryDetails_address.setText(rs.getString("Address"));
            dmDeliveryDetails_vehicleID.setText(rs.getString("vehicleId"));

            qry = "SELECT delivery_vehicle.category FROM delivery_vehicle WHERE delivery_vehicle.vehicleID = '" + dmDeliveryDetails_vehicleID.getText() + "'";
            rs = ShakithDBConnect.search(qry);
            rs.first();
            dmDeliveryDetailsDisc_type.setText(rs.getString("category"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDelivery_setValues(ArrayList<String> li) {
        dmupdateDelivery_DeliveryID.setText(li.get(0));
        dmupdateDelivery_billID.setText(li.get(1));
        dmupdateDelivery_custName.setText(li.get(2));
        dmupdateDelivery_Date.setText(li.get(3));
        System.out.println(li.get(4));
        dmupdateDelivery_time.setText(li.get(4).substring(0, 3));
        dmupdateDelivery_Label_am.addItem(li.get(4).substring(4, 6));
        dmupdateDelivery_Label_am.setSelectedIndex(dmupdateDelivery_Label_am.getItemCount() - 1);
        dmupdateDelivery_address.setText(li.get(5));
        dmupdateDelivery_town.setText(li.get(6));
        dmupdateDelivery_vehicleID.addItem(li.get(7));
        dmupdateDelivery_vehicleID.setSelectedIndex(dmupdateDelivery_vehicleID.getItemCount() - 1);
        dmupdateDelivery_type.setText(li.get(8));
    }

    private void loadData_vehicleDetails() {
        try {
            DefaultTableModel dtm = (DefaultTableModel) dmVehicleDetails_table.getModel();
            String qur = "SELECT delivery_vehicle.registrationNumber,delivery_vehicle.capacity,delivery_vehicle.category FROM delivery_vehicle";
            ResultSet rs = ShakithDBConnect.search(qur);
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("delivery_vehicle.registrationNumber"));
                v.add(rs.getString("delivery_vehicle.capacity"));
                v.add(rs.getString("delivery_vehicle.category"));
                dtm.addRow(v);

            }
            for (int i = 0; i < dmVehicleDetails_table.getColumnCount(); i++) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(dmVehicleDetails_Label_title.CENTER);
                dmVehicleDetails_table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
        }
    }

    public void setDetails_vehicleDetailsDisc() {
        try {
            String qry = "SELECT * FROM delivery_vehicle WHERE registrationNumber='" + vnum + "'";
            ResultSet rs = ShakithDBConnect.search(qry);
            rs.first();
            dmVehicleDetailsDisc_id.setText(rs.getString("vehicleID"));
            dmVehicleDetailsDisc_register.setText(rs.getString("registrationNumber"));
            dmVehicleDetailsDisc_make.setText(rs.getString("make"));
            dmVehicleDetailsDisc_cate.setText(rs.getString("category"));
            dmVehicleDetailsDisc_fuel.setText(rs.getString("fuelType"));
            dmVehicleDetailsDisc_capa.setText(rs.getString("capacity"));
            dmVehicleDetailsDisc_desc.setText(rs.getString("description"));
            dmVehicleDetailsDisc_date.setText(rs.getString("date"));

            File f = new File(rs.getString("path"));
            BufferedImage img = ImageIO.read(f);
            Image dimg = img.getScaledInstance(dmVehicleDetailsDisc_Label_img.getWidth(), dmVehicleDetailsDisc_Label_img.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            dmVehicleDetailsDisc_Label_img.setIcon(imageIcon);
            p = f.toPath().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateVehicle_setValues(ArrayList<String> li, ImageIcon i, String path) {
        dmUpdateVehicle_vehicleId.setText(li.get(0));
        dmUpdateVehicle_registerNum.setText(li.get(1));
        dmUpdateVehicle_make.setSelectedItem(li.get(2));
        dmUpdateVehicle_category.setSelectedItem(li.get(3));
        dmUpdateVehicle_fuelType.setSelectedItem(li.get(4));
        dmUpdateVehicle_capacity.setText(li.get(5));
        dmUpdateVehicle_desc.setText(li.get(6));
        dmUpdateVehicle_date.setText(li.get(7));
        dmUpdateVehicle_Label_img.setIcon(i);
        this.path = path;
    }

    public void generateReport(String path) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\Report.pdf"));
            document.open();

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("C:\\Users\\Chathura Harshanga\\Documents\\NetBeansProjects\\ITP\\src\\images\\Untitled-3.png");
            //document.add(new Paragraph("image"));
            document.add(image);

            document.add(new Paragraph("Wijesighe Motors", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.RED)));
            document.add(new Paragraph(new Date().toString()));
            document.add(new Paragraph("-----------------------------------------------------------------------------------------------------------------------------"));
            document.add(new Paragraph(""));

            PdfPTable table = new PdfPTable(8);

            PdfPCell cell = new PdfPCell(new Paragraph("Vehicle Details"));
            cell.setColspan(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.GREEN);
            table.addCell(cell);

            table.addCell(new PdfPCell(new Paragraph("ID")));
            table.addCell(new PdfPCell(new Paragraph("Vehicle Number")));
            table.addCell(new PdfPCell(new Paragraph("Model")));
            table.addCell(new PdfPCell(new Paragraph("Fuel Type")));
            table.addCell(new PdfPCell(new Paragraph("Description")));
            table.addCell(new PdfPCell(new Paragraph("Date")));
            table.addCell(new PdfPCell(new Paragraph("Category")));
            table.addCell(new PdfPCell(new Paragraph("Capacity")));

            String query = "SELECT * FROM delivery_vehicle";
            ResultSet rs = ShakithDBConnect.search(query);

            while (rs.next()) {
                table.addCell(rs.getString("vehicleID"));
                table.addCell(rs.getString("registrationNumber"));
                table.addCell(rs.getString("make"));
                table.addCell(rs.getString("fuelType"));
                table.addCell(rs.getString("description"));
                table.addCell(rs.getString("date"));
                table.addCell(rs.getString("category"));
                table.addCell(Integer.toString(rs.getInt("capacity")));

            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateDeliveryReport(String path) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\Report_Delivery.pdf"));
            document.open();

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("C:\\Users\\Chathura Harshanga\\Documents\\NetBeansProjects\\ITP\\src\\images\\Untitled-3.png");
            //document.add(new Paragraph("image"));
            document.add(image);

            document.add(new Paragraph("Wijesighe Motors", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.RED)));
            document.add(new Paragraph(new Date().toString()));
            document.add(new Paragraph("-----------------------------------------------------------------------------------------------------------------------------"));
            document.add(new Paragraph(""));

            PdfPTable table = new PdfPTable(10);

            PdfPCell cell = new PdfPCell(new Paragraph("Delivery Details"));
            cell.setColspan(10);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.GREEN);
            table.addCell(cell);

            table.addCell(new PdfPCell(new Paragraph("ID")));
            table.addCell(new PdfPCell(new Paragraph("Bill ID")));
            table.addCell(new PdfPCell(new Paragraph("Customer Name")));
            table.addCell(new PdfPCell(new Paragraph("Dispatch Date")));
            table.addCell(new PdfPCell(new Paragraph("Dispatch Time")));
            table.addCell(new PdfPCell(new Paragraph("Address")));
            table.addCell(new PdfPCell(new Paragraph("Town")));
            table.addCell(new PdfPCell(new Paragraph("Vehicle ID")));
            table.addCell(new PdfPCell(new Paragraph("Driver ID")));
            table.addCell(new PdfPCell(new Paragraph("Status")));

            String query = "SELECT * FROM deliveries";
            ResultSet rs = ShakithDBConnect.search(query);

            while (rs.next()) {
                table.addCell(rs.getString("deliveryID"));
                table.addCell(Integer.toString(rs.getInt("billId")));
                table.addCell(rs.getString("custName"));
                table.addCell(rs.getString("dispatchDate"));
                table.addCell(rs.getString("dispatchTime"));
                table.addCell(rs.getString("Address"));
                table.addCell(rs.getString("Town"));
                table.addCell(rs.getString("vehicleId"));
                table.addCell(Integer.toString(rs.getInt("driverId")));
                table.addCell(rs.getString("status"));

            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
