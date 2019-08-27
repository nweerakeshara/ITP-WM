/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Billpayment;
import model.Billtype;
import model.CustomersModel;
import model.Employee;
import model.EmployeeLeaveModel;
import model.EmployeeModel;
import model.EmployeeType;
import model.SMModel;
import model.Suppliers;
import net.proteanit.sql.DbUtils;
import service.CusDB;
import service.EmpDB;
import service.IM_Service;
import service.SMServiceImpl;
import service.employeeValidator;

/**
 *
 * @author Thanuja Chamika
 */
public class NewJFrame extends javax.swing.JFrame {
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    EmpDB globalEmpDB = new EmpDB();

    /**
     * Creates new form NewJFrame
     */
    IM_Service IM_table = new IM_Service();
    
    public NewJFrame() {
        initComponents();
        
        setExtendedState(NewJFrame.MAXIMIZED_BOTH);  
        
        showDate();
        showTime();
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
    
    void refreshTable(List sList){
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
    
    void refreshTable2(List sList){
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
        sumPanel16 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        SuMReport = new javax.swing.JButton();
        SuMUpdate = new javax.swing.JButton();
        SuMPlace = new javax.swing.JButton();
        SuMAdd = new javax.swing.JButton();
        CMjPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        AddCustomers = new javax.swing.JButton();
        ManageCustomers = new javax.swing.JButton();
        Reports = new javax.swing.JButton();
        FMjPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        IMjPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        IM_search = new javax.swing.JButton();
        IM_update_delete = new javax.swing.JButton();
        IM_add = new javax.swing.JButton();
        PMjPanel7 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
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
        DMjPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        EMjPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        attendanceEmpNavBtn = new javax.swing.JButton();
        leaveEmpNavBtn = new javax.swing.JButton();
        regEmpNavBtn = new javax.swing.JButton();
        addEmpCatNavBtn = new javax.swing.JButton();
        empLeaveMgmtNavBtn = new javax.swing.JButton();
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
        attendanceEmpCard = new javax.swing.JPanel();
        empDateLabel = new javax.swing.JLabel();
        empSignOutBtn = new javax.swing.JButton();
        empSignInBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        empAttendanceTable = new javax.swing.JTable();
        empLeaveCard = new javax.swing.JPanel();
        empRemLeaveLabel = new javax.swing.JLabel();
        empRemLeavBox = new javax.swing.JTextField();
        empstartDateBox = new javax.swing.JTextField();
        empstartDateLabel = new javax.swing.JLabel();
        empEndDateBox = new javax.swing.JTextField();
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
        smViewReports = new javax.swing.JPanel();
        smGenerateBills = new javax.swing.JPanel();
        smjLabel4 = new javax.swing.JLabel();
        custp = new javax.swing.JTextField();
        smjLabel5 = new javax.swing.JLabel();
        btnNxtGenerateBill = new javax.swing.JButton();
        btnBackGenerateBill1 = new javax.swing.JButton();
        custpaymentmethod = new javax.swing.JComboBox<>();
        btnChkCustId = new javax.swing.JButton();
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
        smRegisterCustomers = new javax.swing.JPanel();
        smReturnHandle = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        smviewreturnstable = new javax.swing.JTable();
        smenterbillid = new javax.swing.JLabel();
        smshowitemid = new javax.swing.JLabel();
        smshowitemname = new javax.swing.JLabel();
        smtxtfieldenterbillid = new javax.swing.JTextField();
        smtxtitemid = new javax.swing.JTextField();
        smtxtitemname = new javax.swing.JTextField();
        smbtnsearchbillid = new javax.swing.JButton();
        smbtndeleteitemreturns = new javax.swing.JButton();
        smsalesidreturns = new javax.swing.JLabel();
        smsalesidreturnsbox = new javax.swing.JTextField();
        smGenerateBills2 = new javax.swing.JPanel();
        smjLabel9 = new javax.swing.JLabel();
        smtotbillamt = new javax.swing.JTextField();
        smjLabel10 = new javax.swing.JLabel();
        smcashamtpaid = new javax.swing.JTextField();
        smjLabelch = new javax.swing.JLabel();
        smchangedis = new javax.swing.JTextField();
        SUMjComboBox3 = new javax.swing.JPanel();
        SUMjComboBox1 = new javax.swing.JComboBox();
        SUMjTextField4 = new javax.swing.JTextField();
        SUMjTextField3 = new javax.swing.JTextField();
        SUMjTextField2 = new javax.swing.JTextField();
        SUMjTextField1 = new javax.swing.JTextField();
        SUMjButton10 = new javax.swing.JButton();
        SUMjButton9 = new javax.swing.JButton();
        SUJMjLabel15 = new javax.swing.JLabel();
        SUMjLabel14 = new javax.swing.JLabel();
        SUMjLabel13 = new javax.swing.JLabel();
        SUMjLabel12 = new javax.swing.JLabel();
        SUMjLabel7 = new javax.swing.JLabel();
        SUMjLabel6 = new javax.swing.JLabel();
        SUMMjTextField3 = new javax.swing.JTextField();
        SUMMjLabel19 = new javax.swing.JLabel();
        SUaddressName = new javax.swing.JTextField();
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
        id_cus = new javax.swing.JLabel();
        name_cus = new javax.swing.JLabel();
        phone_cus = new javax.swing.JLabel();
        address_cus_ad = new javax.swing.JLabel();
        email_cus_ad = new javax.swing.JLabel();
        reset_cus_add = new javax.swing.JButton();
        submit_cuss_add = new javax.swing.JButton();
        cusEmailAdd = new javax.swing.JTextField();
        cusNameAdd = new javax.swing.JTextField();
        cusPhoneAdd = new javax.swing.JTextField();
        cusIdAdd = new javax.swing.JTextField();
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
        IMsearch = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        IM_Stable = new javax.swing.JTable();
        IM_serch_by = new javax.swing.JLabel();
        IM_key = new javax.swing.JTextField();
        IM_Sby = new javax.swing.JComboBox();
        IM_search_btn = new javax.swing.JButton();
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel8.setText("Sales management");

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
                .addContainerGap(561, Short.MAX_VALUE))
        );
        SaMjPanel8Layout.setVerticalGroup(
            SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SaMjPanel8Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SaMjPanel8Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addGroup(SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegisterCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGenerateBills, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(SaMjPanel8Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addGroup(SaMjPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnViewReports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHandleReturns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(749, Short.MAX_VALUE))
        );

        jPanel3.add(SaMjPanel8, "card6");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel12.setText("Suppliers management");

        SuMReport.setText("REPORTS");
        SuMReport.setPreferredSize(new java.awt.Dimension(232, 232));
        SuMReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuMReportActionPerformed(evt);
            }
        });

        SuMUpdate.setText("VIEW/UPDATE/DELETE/SEARCH SUPPLIERS");

        SuMPlace.setText("PLACE RDERS");
        SuMPlace.setPreferredSize(new java.awt.Dimension(232, 232));

        SuMAdd.setText("ADD SUPPLIERS");
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
                .addContainerGap(184, Short.MAX_VALUE)
                .addComponent(SuMAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addGroup(sumPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sumPanel16Layout.createSequentialGroup()
                        .addComponent(SuMUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(SuMPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(SuMReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(615, 615, 615))
                    .addGroup(sumPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        sumPanel16Layout.setVerticalGroup(
            sumPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sumPanel16Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jLabel12)
                .addGap(173, 173, 173)
                .addGroup(sumPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SuMAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuMUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuMPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuMReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(722, Short.MAX_VALUE))
        );

        jPanel3.add(sumPanel16, "card4");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel10.setText("Customer management");

        AddCustomers.setText("Add Customers");
        AddCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCustomersActionPerformed(evt);
            }
        });

        ManageCustomers.setText("Manage Customers");
        ManageCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageCustomersActionPerformed(evt);
            }
        });

        Reports.setText("Reports");

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
                        .addGap(428, 428, 428)
                        .addComponent(AddCustomers)
                        .addGap(156, 156, 156)
                        .addComponent(ManageCustomers)
                        .addGap(166, 166, 166)
                        .addComponent(Reports)))
                .addContainerGap(942, Short.MAX_VALUE))
        );
        CMjPanel10Layout.setVerticalGroup(
            CMjPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CMjPanel10Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151)
                .addGroup(CMjPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddCustomers)
                    .addComponent(ManageCustomers)
                    .addComponent(Reports))
                .addContainerGap(946, Short.MAX_VALUE))
        );

        jPanel3.add(CMjPanel10, "card8");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel11.setText("Feedback management");

        javax.swing.GroupLayout FMjPanel11Layout = new javax.swing.GroupLayout(FMjPanel11);
        FMjPanel11.setLayout(FMjPanel11Layout);
        FMjPanel11Layout.setHorizontalGroup(
            FMjPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FMjPanel11Layout.createSequentialGroup()
                .addGap(550, 550, 550)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(979, Short.MAX_VALUE))
        );
        FMjPanel11Layout.setVerticalGroup(
            FMjPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FMjPanel11Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1146, Short.MAX_VALUE))
        );

        jPanel3.add(FMjPanel11, "card9");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel5.setText("Inventory management");

        IM_search.setText("search");
        IM_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_searchActionPerformed(evt);
            }
        });

        IM_update_delete.setText("update / delete");
        IM_update_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_update_deleteActionPerformed(evt);
            }
        });

        IM_add.setText("add item");
        IM_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_addActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout IMjPanel5Layout = new javax.swing.GroupLayout(IMjPanel5);
        IMjPanel5.setLayout(IMjPanel5Layout);
        IMjPanel5Layout.setHorizontalGroup(
            IMjPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMjPanel5Layout.createSequentialGroup()
                .addGroup(IMjPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IMjPanel5Layout.createSequentialGroup()
                        .addGap(546, 546, 546)
                        .addComponent(jLabel5))
                    .addGroup(IMjPanel5Layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addGroup(IMjPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IM_search, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(IMjPanel5Layout.createSequentialGroup()
                                .addComponent(IM_add, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(IM_update_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(961, Short.MAX_VALUE))
        );
        IMjPanel5Layout.setVerticalGroup(
            IMjPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMjPanel5Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel5)
                .addGap(202, 202, 202)
                .addGroup(IMjPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IM_add, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IM_update_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(112, 112, 112)
                .addComponent(IM_search, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(705, Short.MAX_VALUE))
        );

        jPanel3.add(IMjPanel5, "card3");

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

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calc sal.png"))); // NOI18N
        jButton13.setText("Calculate salary");
        jButton13.setPreferredSize(new java.awt.Dimension(232, 232));

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
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(546, Short.MAX_VALUE))
        );

        PMjPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton10, jButton11, jButton12, jButton13});

        PMjPanel7Layout.setVerticalGroup(
            PMjPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PMjPanel7Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(PMjLabel6)
                .addGap(170, 170, 170)
                .addGroup(PMjPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(753, Short.MAX_VALUE))
        );

        jPanel3.add(PMjPanel7, "card5");

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

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jComboBox2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, employeeList, jComboBox2);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.eid}"), jComboBox2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.amount}"), jTextField2, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Bill amount :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Inserted by (Emp. ID) :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Bill type :");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Bill month :");

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton9.setText("Insert");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
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

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton14.setText("Update");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.pid}"), jTextField4, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

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

        javax.swing.GroupLayout pmInsertBillPaymentsLayout = new javax.swing.GroupLayout(pmInsertBillPayments);
        pmInsertBillPayments.setLayout(pmInsertBillPaymentsLayout);
        pmInsertBillPaymentsLayout.setHorizontalGroup(
            pmInsertBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmInsertBillPaymentsLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(622, Short.MAX_VALUE))
        );
        pmInsertBillPaymentsLayout.setVerticalGroup(
            pmInsertBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmInsertBillPaymentsLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(pmInsertBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(603, Short.MAX_VALUE))
        );

        jPanel3.add(pmInsertBillPayments, "card10");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel9.setText("Delivery management");

        javax.swing.GroupLayout DMjPanel9Layout = new javax.swing.GroupLayout(DMjPanel9);
        DMjPanel9.setLayout(DMjPanel9Layout);
        DMjPanel9Layout.setHorizontalGroup(
            DMjPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DMjPanel9Layout.createSequentialGroup()
                .addGap(372, 372, 372)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1116, Short.MAX_VALUE))
        );
        DMjPanel9Layout.setVerticalGroup(
            DMjPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DMjPanel9Layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(961, Short.MAX_VALUE))
        );

        jPanel3.add(DMjPanel9, "card7");

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
                        .addGap(253, 253, 253)
                        .addGroup(EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(EMjPanel4Layout.createSequentialGroup()
                                .addComponent(attendanceEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addEmpCatNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EMjPanel4Layout.createSequentialGroup()
                                .addComponent(regEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(142, 142, 142)
                                .addComponent(leaveEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(160, 160, 160)
                        .addComponent(empLeaveMgmtNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        EMjPanel4Layout.setVerticalGroup(
            EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EMjPanel4Layout.createSequentialGroup()
                .addGroup(EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EMjPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EMjPanel4Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addGroup(EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(regEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(leaveEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveMgmtNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(114, 114, 114)
                .addGroup(EMjPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addEmpCatNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attendanceEmpNavBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(EMjPanel4, "card2");

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(458, Short.MAX_VALUE))
        );
        addEmpCard1Layout.setVerticalGroup(
            addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addEmpCard1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jSeparator1)
                .addGap(35, 35, 35))
            .addGroup(addEmpCard1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(addEmpCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addEmpCard1Layout.createSequentialGroup()
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
                            .addComponent(registerEmpBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(606, Short.MAX_VALUE))
        );

        jPanel3.add(addEmpCard1, "addEmpCard1");

        attendanceEmpCard.setBackground(new java.awt.Color(51, 51, 51));

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
                .addGap(567, 567, 567)
                .addGroup(attendanceEmpCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(attendanceEmpCardLayout.createSequentialGroup()
                        .addComponent(empSignInBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                        .addComponent(empSignOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(empDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap(766, Short.MAX_VALUE))
        );
        attendanceEmpCardLayout.setVerticalGroup(
            attendanceEmpCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attendanceEmpCardLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(empDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 557, Short.MAX_VALUE)
                .addGroup(attendanceEmpCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empSignInBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empSignOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        jPanel3.add(attendanceEmpCard, "attendanceEmpCard");

        empLeaveCard.setBackground(new java.awt.Color(51, 51, 51));

        empRemLeaveLabel.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empRemLeaveLabel.setForeground(new java.awt.Color(255, 255, 255));
        empRemLeaveLabel.setText("Remaining Annual Leave: ");

        empRemLeavBox.setBackground(new java.awt.Color(51, 51, 51));
        empRemLeavBox.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empRemLeavBox.setForeground(new java.awt.Color(255, 255, 255));
        empRemLeavBox.setBorder(null);
        empRemLeavBox.setDisabledTextColor(new java.awt.Color(255, 255, 255));

        empstartDateBox.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        empstartDateLabel.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        empstartDateLabel.setForeground(new java.awt.Color(255, 255, 255));
        empstartDateLabel.setText("Start Date");

        empEndDateBox.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

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
                        .addGap(18, 18, 18)
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(empEndDateBox, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empDaysBox, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empRemLeavBox, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empstartDateBox, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveStatusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(219, 219, 219)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(empLeaveCardLayout.createSequentialGroup()
                        .addGap(602, 602, 602)
                        .addComponent(empLeaveRequestBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(412, Short.MAX_VALUE))
        );
        empLeaveCardLayout.setVerticalGroup(
            empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empLeaveCardLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, empLeaveCardLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(empLeaveCardLayout.createSequentialGroup()
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empRemLeaveLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empRemLeavBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empstartDateBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empstartDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empEndDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empEndDateBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empDaysLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empDaysBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(empReasonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(empLeaveCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(empLeaveStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empLeaveStatusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 491, Short.MAX_VALUE)
                        .addComponent(empLeaveRequestBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89))))
        );

        jPanel3.add(empLeaveCard, "empLeaveCard");

        addCatergoryPanel.setBackground(new java.awt.Color(51, 51, 51));

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
                .addGap(487, 487, 487)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(504, Short.MAX_VALUE))
        );
        addCatergoryPanelLayout.setVerticalGroup(
            addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addCatergoryPanelLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empTypeIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empTypeIdBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(addCatergoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addCatergoryPanelLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(empAddTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addCatergoryPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(empTypeBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)))
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
                            .addComponent(deleteEmpTypeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(663, Short.MAX_VALUE))
        );

        jPanel3.add(addCatergoryPanel, "addCatergoryPanel");

        empLeaveMgmtPanel.setBackground(new java.awt.Color(51, 51, 51));

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

        javax.swing.GroupLayout empLeaveMgmtPanelLayout = new javax.swing.GroupLayout(empLeaveMgmtPanel);
        empLeaveMgmtPanel.setLayout(empLeaveMgmtPanelLayout);
        empLeaveMgmtPanelLayout.setHorizontalGroup(
            empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(empReasonLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empDaysLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empEndDateLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empstartDateLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empEndDateLabel_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empstartDateLabel_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empLeaveReqIDLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                            .addComponent(empApproveRequestBtn_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(empRejectRequestBtn_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(empEndDateBox_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(empID_mgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(empEndDateBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(empDaysBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(empstartDateBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(empLeaveReqID_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(396, Short.MAX_VALUE))
        );
        empLeaveMgmtPanelLayout.setVerticalGroup(
            empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, empLeaveMgmtPanelLayout.createSequentialGroup()
                .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(empLeaveMgmtPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jScrollPane8))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empstartDateBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empstartDateLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empEndDateLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empEndDateBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empDaysLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empDaysBox_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(empReasonLabel_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37)
                .addGroup(empLeaveMgmtPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empApproveRequestBtn_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empRejectRequestBtn_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(112, 112, 112))
        );

        jPanel3.add(empLeaveMgmtPanel, "empLeaveMgmtPanel");

        smViewReports.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout smViewReportsLayout = new javax.swing.GroupLayout(smViewReports);
        smViewReports.setLayout(smViewReportsLayout);
        smViewReportsLayout.setHorizontalGroup(
            smViewReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2031, Short.MAX_VALUE)
        );
        smViewReportsLayout.setVerticalGroup(
            smViewReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1268, Short.MAX_VALUE)
        );

        jPanel3.add(smViewReports, "smViewReports");

        smGenerateBills.setBackground(new java.awt.Color(255, 255, 255));

        smjLabel4.setText("Enter Customer Mobile Number");

        smjLabel5.setText("Enter Payment Method for Bill");

        btnNxtGenerateBill.setText("Proceed");
        btnNxtGenerateBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNxtGenerateBillActionPerformed(evt);
            }
        });

        btnBackGenerateBill1.setText("Back");
        btnBackGenerateBill1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackGenerateBill1ActionPerformed(evt);
            }
        });

        custpaymentmethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Cheque", "Card" }));

        btnChkCustId.setText("Go");
        btnChkCustId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChkCustIdActionPerformed(evt);
            }
        });

        smCustIdStatus.setForeground(new java.awt.Color(255, 0, 0));

        smjLabel6.setText("Enter  Refunds");

        smdisref.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                smdisrefKeyReleased(evt);
            }
        });

        smjLabel7.setText("Enter Item Id");

        smjLabel8.setText("Enter Item Qty");

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

        smbtnadditem.setText("Add Item");
        smbtnadditem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smbtnadditemActionPerformed(evt);
            }
        });

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

        smreset.setText("Reset");
        smreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smresetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout smGenerateBillsLayout = new javax.swing.GroupLayout(smGenerateBills);
        smGenerateBills.setLayout(smGenerateBillsLayout);
        smGenerateBillsLayout.setHorizontalGroup(
            smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, smGenerateBillsLayout.createSequentialGroup()
                        .addComponent(smjLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(custpaymentmethod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addComponent(btnBackGenerateBill1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(smreset, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNxtGenerateBill, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addComponent(smjLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(smdisref, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addComponent(smjLabel4)
                        .addGap(51, 51, 51)
                        .addComponent(custp, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChkCustId))
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(smjLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(smjLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(smitemid, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(smitemqty, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(smbtnadditem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(smbtndeleteitem, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(1508, 1508, 1508))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, smGenerateBillsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(smCustIdStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1543, 1543, 1543))
            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(smqtystatus, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(smdisrefstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(918, 918, 918))
        );
        smGenerateBillsLayout.setVerticalGroup(
            smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(smGenerateBillsLayout.createSequentialGroup()
                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(smjLabel4)
                            .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(custp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnChkCustId)))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 660, Short.MAX_VALUE)
                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(smjLabel5)
                            .addComponent(custpaymentmethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(smjLabel6)
                            .addComponent(smdisref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(smdisrefstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(smGenerateBillsLayout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addGroup(smGenerateBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnNxtGenerateBill)
                                    .addComponent(smreset)
                                    .addComponent(btnBackGenerateBill1))))
                        .addGap(102, 102, 102))))
        );

        jPanel3.add(smGenerateBills, "smGenerateBills");

        smRegisterCustomers.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout smRegisterCustomersLayout = new javax.swing.GroupLayout(smRegisterCustomers);
        smRegisterCustomers.setLayout(smRegisterCustomersLayout);
        smRegisterCustomersLayout.setHorizontalGroup(
            smRegisterCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2031, Short.MAX_VALUE)
        );
        smRegisterCustomersLayout.setVerticalGroup(
            smRegisterCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1268, Short.MAX_VALUE)
        );

        jPanel3.add(smRegisterCustomers, "smRegisterCustomers");

        smReturnHandle.setBackground(new java.awt.Color(255, 255, 255));

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

        smenterbillid.setText("Enter Bill Id");

        smshowitemid.setText("Item Id");

        smshowitemname.setText("Item Name");

        smbtnsearchbillid.setText("Search");
        smbtnsearchbillid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smbtnsearchbillidActionPerformed(evt);
            }
        });

        smbtndeleteitemreturns.setText("Delete");
        smbtndeleteitemreturns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smbtndeleteitemreturnsActionPerformed(evt);
            }
        });

        smsalesidreturns.setText("Sales Id");

        javax.swing.GroupLayout smReturnHandleLayout = new javax.swing.GroupLayout(smReturnHandle);
        smReturnHandle.setLayout(smReturnHandleLayout);
        smReturnHandleLayout.setHorizontalGroup(
            smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, smReturnHandleLayout.createSequentialGroup()
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
                    .addComponent(smtxtitemname)
                    .addComponent(smsalesidreturnsbox))
                .addGap(85, 85, 85)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1031, Short.MAX_VALUE))
        );
        smReturnHandleLayout.setVerticalGroup(
            smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(smReturnHandleLayout.createSequentialGroup()
                .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(smReturnHandleLayout.createSequentialGroup()
                        .addGap(183, 183, 183)
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
                    .addGroup(smReturnHandleLayout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(smReturnHandleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(smtxtitemname, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(smshowitemname, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(smbtndeleteitemreturns, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(663, Short.MAX_VALUE))
        );

        jPanel3.add(smReturnHandle, "smReturnHandle");

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
                .addContainerGap(973, Short.MAX_VALUE))
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
                .addContainerGap(821, Short.MAX_VALUE))
        );

        jPanel3.add(smGenerateBills2, "smGenerateBills2");

        SUMjComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Preferred", "Emergency", " " }));

        SUMjTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SUMjTextField2ActionPerformed(evt);
            }
        });

        SUMjButton10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SUMjButton10.setText("RESET");
        SUMjButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SUMjButton10ActionPerformed(evt);
            }
        });

        SUMjButton9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SUMjButton9.setText("ADD");
        SUMjButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SUMjButton9ActionPerformed(evt);
            }
        });

        SUJMjLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SUJMjLabel15.setText("STATUS            :");

        SUMjLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SUMjLabel14.setText("TYPE                :");

        SUMjLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SUMjLabel13.setText("PHONE NUMBER :");

        SUMjLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SUMjLabel12.setText("E-MAIL              :");

        SUMjLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SUMjLabel7.setText("NAME                :");

        SUMjLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SUMjLabel6.setText("SUPPLIER ID       :");

        SUMMjLabel19.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SUMMjLabel19.setText("ADDRESS          :");

        SUaddressName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SUaddressNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SUMjComboBox3Layout = new javax.swing.GroupLayout(SUMjComboBox3);
        SUMjComboBox3.setLayout(SUMjComboBox3Layout);
        SUMjComboBox3Layout.setHorizontalGroup(
            SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SUMjComboBox3Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(SUMjComboBox3Layout.createSequentialGroup()
                        .addComponent(SUMjButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addComponent(SUMjButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SUMjComboBox3Layout.createSequentialGroup()
                        .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SUJMjLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SUMjLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SUMjLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SUMjLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SUMjLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SUMjLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SUMMjLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(48, 48, 48)
                        .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SUMjTextField1)
                            .addComponent(SUMjTextField2)
                            .addComponent(SUMjTextField3)
                            .addComponent(SUMjTextField4)
                            .addComponent(SUMjComboBox1, 0, 243, Short.MAX_VALUE)
                            .addComponent(SUMMjTextField3)
                            .addComponent(SUaddressName))))
                .addContainerGap(1488, Short.MAX_VALUE))
        );
        SUMjComboBox3Layout.setVerticalGroup(
            SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SUMjComboBox3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SUMjLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(SUMjTextField1))
                .addGap(18, 18, 18)
                .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SUMjTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SUMjLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SUMjLabel12)
                    .addComponent(SUMjTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SUMjLabel13)
                    .addComponent(SUMjTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SUMjLabel14)
                    .addComponent(SUMjComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SUJMjLabel15)
                    .addComponent(SUMMjTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SUaddressName)
                    .addComponent(SUMMjLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 673, Short.MAX_VALUE)
                .addGroup(SUMjComboBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SUMjButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SUMjButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        jPanel3.add(SUMjComboBox3, "SUMaddsuppliers");

        jLabel19.setText("Search");

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
        jRadioButton1.setText("By month");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("By type");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("By employee id");

        javax.swing.GroupLayout pmSearchBillPaymentsLayout = new javax.swing.GroupLayout(pmSearchBillPayments);
        pmSearchBillPayments.setLayout(pmSearchBillPaymentsLayout);
        pmSearchBillPaymentsLayout.setHorizontalGroup(
            pmSearchBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmSearchBillPaymentsLayout.createSequentialGroup()
                .addGroup(pmSearchBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pmSearchBillPaymentsLayout.createSequentialGroup()
                        .addGap(392, 392, 392)
                        .addComponent(jRadioButton1)
                        .addGap(78, 78, 78)
                        .addComponent(jRadioButton2)
                        .addGap(80, 80, 80)
                        .addComponent(jRadioButton3))
                    .addGroup(pmSearchBillPaymentsLayout.createSequentialGroup()
                        .addGap(371, 371, 371)
                        .addGroup(pmSearchBillPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pmSearchBillPaymentsLayout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(1208, Short.MAX_VALUE))
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
                .addContainerGap(599, Short.MAX_VALUE))
        );

        jPanel3.add(pmSearchBillPayments, "pmSearchBillPayments");

        head_add_cus.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        head_add_cus.setText("Add Customers");

        id_cus.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        id_cus.setText("ID");

        name_cus.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        name_cus.setText("Name");

        phone_cus.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        phone_cus.setText("Address");

        address_cus_ad.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        address_cus_ad.setText("Phone");

        email_cus_ad.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
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

        submit_cuss_add.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        submit_cuss_add.setText("Submit");
        submit_cuss_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_cuss_addActionPerformed(evt);
            }
        });

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
                .addGap(67, 67, 67)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cmAddCusLayout.createSequentialGroup()
                        .addComponent(head_add_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cmAddCusLayout.createSequentialGroup()
                        .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_cus)
                            .addComponent(name_cus)
                            .addComponent(address_cus_ad)
                            .addComponent(phone_cus)
                            .addComponent(email_cus_ad))
                        .addGap(75, 75, 75)
                        .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cmAddCusLayout.createSequentialGroup()
                                .addComponent(reset_cus_add, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(submit_cuss_add, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(cmAddCusLayout.createSequentialGroup()
                                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cusNameAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cusIdAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                    .addComponent(ID_Val))
                                .addContainerGap(1456, Short.MAX_VALUE))))))
        );
        cmAddCusLayout.setVerticalGroup(
            cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmAddCusLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(head_add_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id_cus)
                    .addComponent(cusIdAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ID_Val))
                .addGap(18, 18, 18)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cusNameAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name_cus)
                    .addComponent(name_val))
                .addGap(18, 18, 18)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cusPhoneAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address_cus_ad)
                    .addComponent(phone_val))
                .addGap(18, 18, 18)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phone_cus)
                    .addComponent(cusAddAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address_val))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cusEmailAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email_cus_ad)
                    .addComponent(email_val))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(cmAddCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reset_cus_add, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submit_cuss_add, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(761, Short.MAX_VALUE))
        );

        jPanel3.add(cmAddCus, "cmAddCus");

        ID_cus.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        ID_cus.setText("ID");

        manage_cus.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        manage_cus.setText("Manage Customers");

        cancel_cus.setBackground(new java.awt.Color(255, 255, 255));
        cancel_cus.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cancel_cus.setForeground(new java.awt.Color(51, 51, 0));
        cancel_cus.setText("Cancel");
        cancel_cus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_cusActionPerformed(evt);
            }
        });

        address_cus.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        address_cus.setText("Address");

        phone_cus_u.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        phone_cus_u.setText("Phone");

        email_cus_U.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        email_cus_U.setText("Email");

        name_cus_t.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
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

        labCusID.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
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

        javax.swing.GroupLayout cmUDCusLayout = new javax.swing.GroupLayout(cmUDCus);
        cmUDCus.setLayout(cmUDCusLayout);
        cmUDCusLayout.setHorizontalGroup(
            cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmUDCusLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(cmUDCusLayout.createSequentialGroup()
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cmUDCusLayout.createSequentialGroup()
                                .addComponent(cancel_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(delete_cus_U, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(update_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(cmUDCusLayout.createSequentialGroup()
                                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ID_cus)
                                    .addComponent(name_cus_t)
                                    .addComponent(email_cus_U)
                                    .addComponent(phone_cus_u)
                                    .addComponent(address_cus))
                                .addGap(75, 75, 75)
                                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(name_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(phoneBoxCus, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(emailBoxCus, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(adress_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labCusID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(name_val_U)
                                    .addComponent(email_val_U3)
                                    .addComponent(address_val_U2)
                                    .addComponent(phone_val_U1))))
                        .addGap(109, 109, 109)
                        .addComponent(cus_view_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cmUDCusLayout.createSequentialGroup()
                        .addComponent(manage_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2087, Short.MAX_VALUE))))
        );
        cmUDCusLayout.setVerticalGroup(
            cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmUDCusLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(manage_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cmUDCusLayout.createSequentialGroup()
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ID_cus)
                            .addComponent(labCusID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(name_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(name_cus_t)
                            .addComponent(name_val_U))
                        .addGap(18, 18, 18)
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneBoxCus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phone_cus_u)
                            .addComponent(phone_val_U1))
                        .addGap(18, 18, 18)
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(address_cus)
                            .addComponent(adress_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(address_val_U2))
                        .addGap(18, 18, 18)
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emailBoxCus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(email_cus_U)
                            .addComponent(email_val_U3))
                        .addGap(41, 41, 41)
                        .addGroup(cmUDCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cancel_cus, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(delete_cus_U, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(update_cus_u, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cus_view_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(447, Short.MAX_VALUE))
        );

        jPanel3.add(cmUDCus, "cmUDCus");

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

        IM_serch_by.setText("search by : ");

        IM_Sby.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "id", "name", "brand", "less than qty", "greater than qty" }));

        IM_search_btn.setText("search");
        IM_search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IM_search_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout IMsearchLayout = new javax.swing.GroupLayout(IMsearch);
        IMsearch.setLayout(IMsearchLayout);
        IMsearchLayout.setHorizontalGroup(
            IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMsearchLayout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(IM_serch_by)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(IM_Sby, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(IM_key, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(IM_search_btn)
                .addContainerGap(1275, Short.MAX_VALUE))
            .addGroup(IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(IMsearchLayout.createSequentialGroup()
                    .addGap(111, 111, 111)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(858, Short.MAX_VALUE)))
        );
        IMsearchLayout.setVerticalGroup(
            IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMsearchLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IM_serch_by)
                    .addComponent(IM_key, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IM_Sby, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IM_search_btn))
                .addContainerGap(1174, Short.MAX_VALUE))
            .addGroup(IMsearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(IMsearchLayout.createSequentialGroup()
                    .addGap(119, 119, 119)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1136, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel3.add(IMsearch, "IM_search");

        IM_name_lbl.setText("Name");

        IM_type_lbl.setText("Brand");

        IM_qty_lbl.setText("Qty");

        IM_bprice_lbl.setText("Buying price");

        IM_sprice_lbl.setText("Selling price");

        IM_ID_lbl.setText("ID");

        IM_update_btn.setBackground(new java.awt.Color(51, 255, 0));
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

        IM_delete_btn.setBackground(new java.awt.Color(255, 51, 51));
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

        javax.swing.GroupLayout IMupdateLayout = new javax.swing.GroupLayout(IMupdate);
        IMupdate.setLayout(IMupdateLayout);
        IMupdateLayout.setHorizontalGroup(
            IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMupdateLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IMupdateLayout.createSequentialGroup()
                        .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(IM_name_lbl)
                                .addComponent(IM_sprice_lbl, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(IM_ID_lbl)
                                .addComponent(IM_qty_lbl)
                                .addComponent(IM_bprice_lbl))
                            .addComponent(IM_type_lbl)
                            .addComponent(IM_update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(IM_U_name_error_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(IM_U_Bprice_error_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(I_Uname)
                                    .addComponent(I_ID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(I_USprice)
                                    .addComponent(I_Uqty)
                                    .addComponent(I_UBprice)
                                    .addComponent(I_Utype)
                                    .addComponent(IM_U_brand_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_U_qty_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IM_U_Sprice_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(IM_delete_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(IM_U_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IM_U_update, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(843, Short.MAX_VALUE))
        );
        IMupdateLayout.setVerticalGroup(
            IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMupdateLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(IM_ID_lbl)
                    .addComponent(I_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(IMupdateLayout.createSequentialGroup()
                        .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(I_Uname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IM_name_lbl))
                        .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(IMupdateLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(IMupdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(IM_type_lbl)
                                    .addComponent(I_Utype, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(IMupdateLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IM_U_name_error_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(IM_U_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(813, Short.MAX_VALUE))
        );

        jPanel3.add(IMupdate, "IM_update");

        IM_name.setText("Name");

        IM_type.setText("Brand");

        IM_qty.setText("Qty");

        IM_bprice.setText("Buying price");

        IM_sprice.setText("Selling price");

        IM_add_btn.setBackground(new java.awt.Color(0, 102, 255));
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

        javax.swing.GroupLayout IMaddLayout = new javax.swing.GroupLayout(IMadd);
        IMadd.setLayout(IMaddLayout);
        IMaddLayout.setHorizontalGroup(
            IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMaddLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IMaddLayout.createSequentialGroup()
                        .addComponent(IM_qty)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                    .addComponent(I_Sprice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                                    .addComponent(IM_A_Bprice_error_lbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(I_Bprice, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(IM_A_qty_error_lbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(I_qty, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(IM_A_brand_error_lbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IM_A_name_error_lbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(I_name, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(I_type, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(IMaddLayout.createSequentialGroup()
                                    .addGap(218, 218, 218)
                                    .addComponent(IM_add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(IM_A_success, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(92, 92, 92)))
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(724, Short.MAX_VALUE))
        );
        IMaddLayout.setVerticalGroup(
            IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IMaddLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(IMaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(IM_add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(IM_A_success, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(838, Short.MAX_VALUE))
        );

        jPanel3.add(IMadd, "IM_add");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 2031, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 2031, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        viewPanel("card4");
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

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        Billpayment b = new Billpayment();
        
       
        
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
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        viewPanel("card10");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void attendanceEmpNavBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendanceEmpNavBtnActionPerformed

        viewPanel("attendanceEmpCard");
        globalEmpDB.empAttendanceTableLoad(jTable1);

        //set system date for attendance
        empDateLabel.setText(java.util.Calendar.getInstance().getTime().toString());
    }//GEN-LAST:event_attendanceEmpNavBtnActionPerformed

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

    private void regEmpNavBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regEmpNavBtnActionPerformed

        viewPanel("addEmpCard1");

        globalEmpDB.empTableLoad(empTable);
        
        ResultSet typeRS = globalEmpDB.getEmpCatergory();
        
        try {
            
            Vector<EmployeeType> vector = new Vector<>();
            
            while(typeRS.next()){
                                
                EmployeeType e1 = new EmployeeType(typeRS.getInt("id"), typeRS.getString("description"));
                
                vector.addElement(e1);
                
                
            }
            
            emptypeComboBox.setModel(new DefaultComboBoxModel(vector));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_regEmpNavBtnActionPerformed

    private void addEmpCatNavBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmpCatNavBtnActionPerformed
        viewPanel("addCatergoryPanel");
        globalEmpDB.empCatergoryTableLoad(empCatergoryTable);
    }//GEN-LAST:event_addEmpCatNavBtnActionPerformed

    private void empLeaveMgmtNavBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empLeaveMgmtNavBtnActionPerformed
        viewPanel("empLeaveMgmtPanel");
        globalEmpDB.empLeaveRequestTableLoad(empLeaveRequestTable);
    }//GEN-LAST:event_empLeaveMgmtNavBtnActionPerformed

    private void empAddressBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empAddressBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_empAddressBoxActionPerformed

    private void deleteEmpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEmpBtnActionPerformed

        int response = JOptionPane.showConfirmDialog(null, "Do you really want to delete selected employee?");

        if(response == 0){

            String id  = empIdBox.getText();

            globalEmpDB.deleteEmp(id);
            globalEmpDB.empTableLoad(empTable);
        }
    }//GEN-LAST:event_deleteEmpBtnActionPerformed

    private void registerEmpBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerEmpBtn1ActionPerformed

        //Get data from form
        try{
            
            EmployeeType e2;
            e2 = (EmployeeType)emptypeComboBox.getSelectedItem();
            
            String name = empNameBox.getText();
            String nic = empNICBox.getText();
            String address = empAddressBox.getText();
            int phone = Integer.parseInt(empPhoneBox.getText());
            String email = empEmailBox.getText();
            int type = e2.getEmpTypeId();
            int phoneLength = Integer.toString(phone).length();

            EmpDB db = new EmpDB();

            //Check for duplicate entries
            if(db.checkRepeat(nic) == false){
                //Form field validation
                //Check empty fields
                if(name.equals("")||nic.equals("")||address.equals("")||email.equals("")|| phoneLength == 0){

                    JOptionPane.showMessageDialog(addEmpCard1, "Fill all form fields! Enter 'None' if non-existant");

                }
                //Validate email
                else if(!employeeValidator.isValidEmailAddress(email)){

                    JOptionPane.showMessageDialog(addEmpCard1, "Enter valid email address!");
                }
                else if(phoneLength != 10){

                    JOptionPane.showMessageDialog(addEmpCard1, "Enter phone number with 10 digits!");
                }
                else if(nic.length() < 10 || nic.length() > 12){

                    JOptionPane.showMessageDialog(addEmpCard1, "NIC must contain between 10-12 characters!");
                }

                //If no errors
                else{
                    int response = JOptionPane.showConfirmDialog(null, "Do you really want to register this employee?");
                    if(response == 0){
                        //Send data to model
                        EmployeeModel e1 = new EmployeeModel(name, nic, address, email, type, phone);

                        //Call method to access DB

                        db.addEmployee(e1);

                        globalEmpDB.empTableLoad(empTable);
                    }
                }
            }
            else{

                JOptionPane.showMessageDialog(addEmpCard1, "Employee already exists!");
            }
        }
        //Catch converting invalid phone numbers
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(addEmpCard1, "Enter valid phone number!");
        }

    }//GEN-LAST:event_registerEmpBtn1ActionPerformed

    private void updateEmpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEmpBtnActionPerformed

        int response = JOptionPane.showConfirmDialog(null, "Do you really want to update?");
        if(response == 0){

            String empID = empIdBox.getText();
            String empName = empNameBox.getText();
            String empNic = empNICBox.getText();
            String empAddress = empAddressBox.getText();
            int phone = Integer.parseInt(empPhoneBox.getText());
            String empEmail = empEmailBox.getText();
            int empType = emptypeComboBox.getSelectedIndex();
            int phoneLength = Integer.toString(phone).length();

            if(empName.equals("")||empNic.equals("")||empAddress.equals("")||empEmail.equals("")|| phoneLength == 0){

                JOptionPane.showMessageDialog(addEmpCard1, "Fill all form fields! Enter 'None' if non-existant");

            }
            //Validate email
            else if(!employeeValidator.isValidEmailAddress(empEmail)){

                JOptionPane.showMessageDialog(addEmpCard1, "Enter valid email address!");
            }
            else if(phoneLength != 10){

                JOptionPane.showMessageDialog(addEmpCard1, "Enter phone number with 10 digits!");
            }
            else if(empNic.length() < 10 || empNic.length() > 12){

                JOptionPane.showMessageDialog(addEmpCard1, "NIC must contain between 10-12 characters!");
            }

            else{
                //Send data to model
                EmployeeModel e1 = new EmployeeModel(empName, empNic, empAddress, empEmail,empType, phone);
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

    private void empSignOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empSignOutBtnActionPerformed

        String popupMsg = "Sign out for "+java.util.Calendar.getInstance().getTime().toString()+" ?";

        int signInResponse = JOptionPane.showConfirmDialog(null, popupMsg);

        //temp hard coded id must be changed
        int id = 5;

        if(signInResponse == 0){

            globalEmpDB.empSignIn(id);

        }

        globalEmpDB.empAttendanceTableLoad(jTable1);

    }//GEN-LAST:event_empSignOutBtnActionPerformed

    private void empSignInBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empSignInBtnActionPerformed

        String popupMsg = "Sign in for "+java.util.Calendar.getInstance().getTime().toString()+" ?";

        int signInResponse = JOptionPane.showConfirmDialog(null, popupMsg);

        //temp hard coded id must be changed
        int id = 5;

        if(signInResponse == 0){

            globalEmpDB.empSignIn(id);

        }

        globalEmpDB.empAttendanceTableLoad(empAttendanceTable);

    }//GEN-LAST:event_empSignInBtnActionPerformed

    private void empLeaveRequestBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empLeaveRequestBtnActionPerformed
        try{
            int empID = 5;
            int remLeave = Integer.parseInt(empRemLeavBox.getText());
            String startDate = empstartDateBox.getText();
            String endDate = empEndDateBox.getText();
            String reason = empReasonText.getText();
            int noOfDays = Integer.parseInt(empDaysBox.getText());

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(cal.getTime());

            //Validate empty fields
            if(startDate.equals("") || endDate.equals("") || reason.equals("")){
                JOptionPane.showMessageDialog(null, "Please fill all form fields!");
            }
            //Validate number of days
            else if(noOfDays == 0 || noOfDays > remLeave){
                JOptionPane.showMessageDialog(null, "Number of days cannot be zero or exceed remaining leave amount!");
            }
            //Validate date format
            else if(!employeeValidator.isValidDate(startDate)){

                JOptionPane.showMessageDialog(null, "Please use yyyy/MM/dd format to enter starting date and ending date!");
            }
            //Validate date format
            else if(!employeeValidator.isValidDate(endDate)){

                JOptionPane.showMessageDialog(null, "Please use yyyy/MM/dd format to enter starting date and ending date!");
            }
            //Validate start date before end date
            else if(!employeeValidator.isDateGreater(startDate, endDate)){

                JOptionPane.showMessageDialog(null, "Starting date must be before ending date!");

            }
            //Validate startDate is greater than current date
            else if(employeeValidator.isDateGreater(startDate, currentDate)){

                JOptionPane.showMessageDialog(null, "Starting date cannot be before todays date!");

            }
            //Check overlap
            else if (globalEmpDB.checkLeaveRequestOverlap(empID, startDate, endDate)){

                JOptionPane.showMessageDialog(null, "Request overlaps a previous request!");

            }
            else{
                EmployeeLeaveModel empLeave = new EmployeeLeaveModel(empID, startDate, endDate, noOfDays, reason);

                globalEmpDB.addEmpLeaveRequest(empLeave);

                globalEmpDB.empLeaveRequestTableLoad(empRequestTable);
            }
        }
        catch(NumberFormatException e){
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

        empstartDateBox.setText(startDate);
        empEndDateBox.setText(endDate);
        empDaysBox.setText(nDays);
        empReasonText.setText(reason);
        empLeaveStatusLabel.setText("Request Status");

        if(approved.equals("true")){

            empLeaveStatusBox.setText("  Request Approved  ");
            empLeaveStatusBox.setBackground(Color.GREEN);
            empLeaveStatusBox.setForeground(Color.WHITE);
        }
        else if(rejected.equals("true")){
            empLeaveStatusBox.setText("  Request Rejected  ");
            empLeaveStatusBox.setBackground(Color.RED);
            empLeaveStatusBox.setForeground(Color.WHITE);
        }
        else{
            empLeaveStatusBox.setText("  Request Pending  ");
            empLeaveStatusBox.setBackground(Color.BLUE);
            empLeaveStatusBox.setForeground(Color.WHITE);
        }

    }//GEN-LAST:event_empRequestTableMouseClicked

    private void updateEmpTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEmpTypeBtnActionPerformed

        try{

            int typeId = Integer.parseInt(empTypeIdBox.getText());
            String typeName = empTypeBox1.getText();
            double basicSal = Double.parseDouble(empSalaryBox.getText());
            int vacationDays = Integer.parseInt(empLeaveBox.getText());

            if(typeName.equals("")){

                JOptionPane.showMessageDialog(addEmpCard1, "Employee type cannot be empty!");
            }
            else{
                EmployeeType empType = new EmployeeType(typeName, basicSal, vacationDays);
                empType.setEmpTypeId(typeId);

                globalEmpDB.updateEmpType(empType);

                globalEmpDB.empCatergoryTableLoad(empCatergoryTable);
            }
        }
        catch(NumberFormatException e){

            JOptionPane.showMessageDialog(addEmpCard1, "Please ensure salary and vacation days are numeric!");

        }

    }//GEN-LAST:event_updateEmpTypeBtnActionPerformed

    private void deleteEmpTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEmpTypeBtnActionPerformed

        int typeId = Integer.parseInt(empTypeIdBox.getText());

        globalEmpDB.deleteEmpType(typeId);

        globalEmpDB.empCatergoryTableLoad(empCatergoryTable);

    }//GEN-LAST:event_deleteEmpTypeBtnActionPerformed

    private void addEmpTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmpTypeBtnActionPerformed

        try{
            String empType = empTypeBox1.getText();
            double empSalary = Double.parseDouble(empSalaryBox.getText());
            int empLeave = Integer.parseInt(empLeaveBox.getText());

            if(empType.equals("")){

                JOptionPane.showMessageDialog(null, "Employee Type cannot be empty!");

            }
            else{

                EmployeeType empTypeObj = new EmployeeType(empType,empSalary,empLeave);

                globalEmpDB.addEmpType(empTypeObj);
                globalEmpDB.empCatergoryTableLoad(empCatergoryTable);
            }
        }catch(NumberFormatException e){

            JOptionPane.showMessageDialog(null, "Salary and vaction days must be numeric!");

        }

    }//GEN-LAST:event_addEmpTypeBtnActionPerformed

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

    private void empApproveRequestBtn_mgmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empApproveRequestBtn_mgmtActionPerformed

        try{
            int requestId = Integer.parseInt(empLeaveReqID_mgmt.getText());

            globalEmpDB.approveLeaveRequest(requestId);

            globalEmpDB.empLeaveRequestTableLoad(empLeaveRequestTable);

        }
        catch(Exception e){

            JOptionPane.showMessageDialog(null, "Please select a request first!");

        }

    }//GEN-LAST:event_empApproveRequestBtn_mgmtActionPerformed

    private void empLeaveRequestTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empLeaveRequestTableMouseClicked

        int leaveTableRow = empLeaveRequestTable.getSelectedRow();
        EmployeeModel e1;

        String empId = empLeaveRequestTable.getValueAt(leaveTableRow, 1).toString();
        String requestId = empLeaveRequestTable.getValueAt(leaveTableRow, 0).toString();
        String empName ;
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

    private void empRejectRequestBtn_mgmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empRejectRequestBtn_mgmtActionPerformed

        try{
            int requestId = Integer.parseInt(empLeaveReqID_mgmt.getText());

            globalEmpDB.rejectLeaveRequest(requestId);

            globalEmpDB.empLeaveRequestTableLoad(empLeaveRequestTable);

        }
        catch(Exception e){

            JOptionPane.showMessageDialog(null, "Please select a request first!");

        }
    }//GEN-LAST:event_empRejectRequestBtn_mgmtActionPerformed

    private void btnViewReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewReportsActionPerformed
        // TODO add your handling code here:
        viewPanel("smViewReports");
    }//GEN-LAST:event_btnViewReportsActionPerformed

    private void btnHandleReturnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHandleReturnsActionPerformed
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        smviewreturnstable.setModel(DbUtils.resultSetToTableModel(sms1.deleteallfrmsalestable()));
        smsalesidreturnsbox.setText("");
        smtxtitemid.setText("");
        smtxtitemname.setText("");
        smtxtfieldenterbillid.setText("");
        viewPanel("smReturnHandle");
    }//GEN-LAST:event_btnHandleReturnsActionPerformed

    private void btnRegisterCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterCustomersActionPerformed
        // TODO add your handling code here:
        viewPanel("smRegisterCustomers");
    }//GEN-LAST:event_btnRegisterCustomersActionPerformed

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

    private void btnNxtGenerateBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNxtGenerateBillActionPerformed
        // TODO add your handling code here:
        smtotbillamt.setText("");
        smcashamtpaid.setText("");
        smchangedis.setText("");

        SMModel sm = new SMModel();
        sm.setcustp(custp.getText());
        sm.setReturnAmt(Double.parseDouble(smdisref.getText()));
        sm.setPayType((String) custpaymentmethod.getSelectedItem());
        SMServiceImpl sms1 = new SMServiceImpl();
        sms1.generatebill(sm);
        sms1.deleteallfrmbill();
        viewPanel("smGenerateBills2");
    }//GEN-LAST:event_btnNxtGenerateBillActionPerformed

    private void btnBackGenerateBill1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackGenerateBill1ActionPerformed
        // TODO add your handling code here:
        viewPanel("smMain");
    }//GEN-LAST:event_btnBackGenerateBill1ActionPerformed

    private void btnChkCustIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChkCustIdActionPerformed
        // TODO add your handling code here:
        SMModel sm1 = new SMModel();
        sm1.setcustp(custp.getText());
        SMServiceImpl sms1 = new SMServiceImpl();
        if(sms1.customerTableLoad(sm1)){
            smCustIdStatus.setText("  Valid Customer, Please Continue With the Ongoing Bill");
        }
        else{
            smCustIdStatus.setText("Invalid Customer, Use Anonymous Id or Register Customer");
        }
    }//GEN-LAST:event_btnChkCustIdActionPerformed

    private void smdisrefKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smdisrefKeyReleased
        // TODO add your handling code here:
        try{
            double disref = Double.parseDouble(smdisref.getText());
            smdisrefstatus.setText("");

        }catch(NumberFormatException e){
            smdisrefstatus.setText("Enter a Valid Value");
        }
    }//GEN-LAST:event_smdisrefKeyReleased

    private void smitemqtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smitemqtyKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_smitemqtyKeyPressed

    private void smitemqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smitemqtyKeyReleased
        // TODO add your handling code here:
        try{
            int qtyno = Integer.parseInt(smitemqty.getText());
            smqtystatus.setText("");

        }catch(NumberFormatException e){
            smqtystatus.setText("Enter a Valid Value");
        }
    }//GEN-LAST:event_smitemqtyKeyReleased

    private void smitemqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smitemqtyKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_smitemqtyKeyTyped

    private void smbtnadditemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smbtnadditemActionPerformed
        // TODO add your handling code here:
        SMModel sm1 = new SMModel();
        sm1.setItemId(Integer.parseInt(smitemid.getText()));
        sm1.setQty(Integer.parseInt(smitemqty.getText()));
        SMServiceImpl sms1 = new SMServiceImpl();
        if(sms1.addtobill(sm1)){
            smqtystatus.setText("");
        }
        else{
            smqtystatus.setText("Error");
        }

        smbilltable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitems()));
    }//GEN-LAST:event_smbtnadditemActionPerformed

    private void smbtndeleteitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smbtndeleteitemActionPerformed
        // TODO add your handling code here:
        SMModel sm1 = new SMModel();
        sm1.setItemId(Integer.parseInt(smitemid.getText()));
        SMServiceImpl sms1 = new SMServiceImpl();
        sms1.deleteitemfrmbill(sm1);
        smbilltable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitems()));
    }//GEN-LAST:event_smbtndeleteitemActionPerformed

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

    private void smresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smresetActionPerformed
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        sms1.deleteallfrmbill();
        smbilltable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitems()));
    }//GEN-LAST:event_smresetActionPerformed

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
        smtxtitemname.setText(smqtyr);
    }//GEN-LAST:event_smviewreturnstableMouseClicked

    private void smbtnsearchbillidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smbtnsearchbillidActionPerformed
        // TODO add your handling code here:
        SMModel sm1 = new SMModel();
        sm1.setBillId(Integer.parseInt(smtxtfieldenterbillid.getText()));
        SMServiceImpl sms1 = new SMServiceImpl();
        sms1.getbillitemsforreturns(sm1);
        smviewreturnstable.setModel(DbUtils.resultSetToTableModel(sms1.getbillitemsforreturns(sm1)));
    }//GEN-LAST:event_smbtnsearchbillidActionPerformed

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

    private void smtotbillamtMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smtotbillamtMouseMoved
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        smtotbillamt.setText(Double.toString(sms1.returnbillamt()));
        String smcashpaid = smcashamtpaid.getText();
        smchangedis.setText(Double.toString(-sms1.returnbillamt() + Double.parseDouble(smcashpaid)));
    }//GEN-LAST:event_smtotbillamtMouseMoved

    private void smchangedisMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smchangedisMouseMoved
        // TODO add your handling code here:
        SMServiceImpl sms1 = new SMServiceImpl();
        smtotbillamt.setText(Double.toString(sms1.returnbillamt()));
        String smcashpaid = smcashamtpaid.getText();
        smchangedis.setText(Double.toString(-sms1.returnbillamt() + Double.parseDouble(smcashpaid)));
    }//GEN-LAST:event_smchangedisMouseMoved

    private void SuMReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuMReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuMReportActionPerformed

    private void SuMAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuMAddActionPerformed
        // TODO add your handling code here:
        viewPanel("SUMaddsuppliers");
    }//GEN-LAST:event_SuMAddActionPerformed

    private void SUMjTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SUMjTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SUMjTextField2ActionPerformed

    private void SUMjButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SUMjButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SUMjButton10ActionPerformed

    private void SUMjButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SUMjButton9ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_SUMjButton9ActionPerformed

    private void SUaddressNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SUaddressNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SUaddressNameActionPerformed

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

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        Billpayment b = ITPPUEntityManager.find(Billpayment.class, Integer.parseInt(jTextField4.getText()));

        int r = JOptionPane.showConfirmDialog(rootPane, "Update : " + b.getPid(), "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

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
        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        viewPanel("pmSearchBillPayments");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
         if (jRadioButton1.isSelected()) {

            //String se = jTextField3.getText();
            //int nu = Integer.parseInt(se);
            try{
                Query q = ITPPUEntityManager.
                //createQuery("SELECT b FROM Billpayment b WHERE b.month like :pTitle");
                createQuery("SELECT b FROM Billpayment b WHERE b.month like :pTitle");
                q.setParameter("pTitle", Integer.parseInt(jTextField3.getText()));
                //q.setParameter("pTitle", "%" + jTextField3.getText() + "%");
                billpaymentList.clear();
                billpaymentList = q.getResultList();
                refreshTable2(billpaymentList);
                
            }catch(NumberFormatException e){
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

    private void AddCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCustomersActionPerformed
        // TODO add your handling code here:
        viewPanel("cmAddCus");
        
    }//GEN-LAST:event_AddCustomersActionPerformed

    private void ManageCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageCustomersActionPerformed
        // TODO add your handling code here:
        CusDB.CustableLoad(cus_view_table);
        viewPanel("cmUDCus");
        System.out.println("I am in");
        
    }//GEN-LAST:event_ManageCustomersActionPerformed

    private void reset_cus_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_cus_addActionPerformed
        // TODO add your handling code here:
        cusIdAdd.setText("");
        cusNameAdd.setText("");
        cusPhoneAdd.setText("");
        cusAddAdd.setText("");
        cusEmailAdd.setText("");

    }//GEN-LAST:event_reset_cus_addActionPerformed

    private void submit_cuss_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_cuss_addActionPerformed
        // TODO add your handling code here:
        String Id = cusIdAdd.getText();
        String name = cusNameAdd.getText();
        String phone = cusPhoneAdd.getText();
        String address = cusAddAdd.getText();
        String email = cusEmailAdd.getText();

        CustomersModel c1 = new CustomersModel(Id, name, phone, address, email);

        int vali = 0;
        if(Id == null || Id.length() == 0){
            vali = 1;
            ID_Val.setText("*Empty");

        }
        else{
            ID_Val.setText("");
        }
        if(name == null || name.length() == 0){
            vali = 1;
            name_val.setText("*Empty");
        }
        else{
            name_val.setText("");
        }
        if(phone == null || phone.length() == 0){
            vali = 1;
            phone_val.setText("*Empty");
        }
        else{
            phone_val.setText("");
        }
        if(email == null || email.length() == 0){
            vali = 1;
            email_val.setText("*Empty");
        }
        else{
            email_val.setText("");
        }
        if(address == null || address.length() == 0){
            vali = 1;
            address_val.setText("*Empty");
        }
        else{
            address_val.setText("");
        }
        if(vali == 0){
            CusDB db = new CusDB();
            db.addCustomers(c1);
            message.setText("Sucessfully Added");
            CusDB.CustableLoad(cus_view_table);
        }
    }//GEN-LAST:event_submit_cuss_addActionPerformed

    private void cancel_cusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_cusActionPerformed
        // TODO add your handling code here:
        name_cus_u.setText("");
        phoneBoxCus.setText("");
        adress_cus_u.setText("");
        emailBoxCus.setText("");
        labCusID.setText("Customer ID");

    }//GEN-LAST:event_cancel_cusActionPerformed

    private void delete_cus_UActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_cus_UActionPerformed
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(null, "Do you really want to update");

        if(x == 0){
            String id = labCusID.getText();

            CustomersModel c1 = new CustomersModel(id);

            CusDB db = new CusDB();
            db.deleteCustomers(c1);
            CusDB.CustableLoad(cus_view_table);

        }

    }//GEN-LAST:event_delete_cus_UActionPerformed

    private void update_cus_uActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_cus_uActionPerformed
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(null, "Do you really want to update");

        if(x == 0){
            String id = labCusID.getText();
            String name = name_cus_u.getText();
            String phone = phoneBoxCus.getText();
            String email = emailBoxCus.getText();
            String address = adress_cus_u.getText();

            CustomersModel c1 = new CustomersModel(id, name, phone, address, email);

            int vali =0;

            if(name == null || name.length() == 0){
                vali = 1;
                name_val_U.setText("*Empty");
            }
            else{
                name_val_U.setText("");
            }
            if(phone == null || phone.length() == 0){
                vali = 1;
                phone_val_U1.setText("*Empty");
            }
            else{
                phone_val_U1.setText("");
            }
            if(email == null || email.length() == 0){
                vali = 1;
                email_val_U3.setText("*Empty");
            }
            else{
                email_val_U3.setText("");
            }
            if(address == null || address.length() == 0){
                vali = 1;
                address_val_U2.setText("*Empty");
            }
            else{
                address_val_U2.setText("");
            }
            if(vali == 0){
                CusDB db = new CusDB();
                db.updateCustomers(c1);
                CusDB.CustableLoad(cus_view_table);

            }
        }

    }//GEN-LAST:event_update_cus_uActionPerformed

    private void cus_view_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cus_view_tableMouseClicked
        // TODO add your handling code here:
        int r =cus_view_table.getSelectedRow();
        String id =  cus_view_table.getValueAt(r,0).toString();
        String name =  cus_view_table.getValueAt(r,1).toString();
        String phone =  cus_view_table.getValueAt(r,2).toString();
        String email =  cus_view_table.getValueAt(r,3).toString();
        String address =  cus_view_table.getValueAt(r,4).toString();

        name_cus_u.setText(name);
        phoneBoxCus.setText(phone);
        adress_cus_u.setText(address);
        emailBoxCus.setText(email);
        labCusID.setText(id);

    }//GEN-LAST:event_cus_view_tableMouseClicked

    private void IM_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_searchActionPerformed

        viewPanel("IM_search");
        IM_Stable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));

    }//GEN-LAST:event_IM_searchActionPerformed

    private void IM_update_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_update_deleteActionPerformed

        viewPanel("IM_update");
        IM_Utable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));

    }//GEN-LAST:event_IM_update_deleteActionPerformed

    private void IM_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_addActionPerformed

        viewPanel("IM_add");
        IM_Atable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));

    }//GEN-LAST:event_IM_addActionPerformed

    private void IM_search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_search_btnActionPerformed

        IM_Service service = new IM_Service();

        String by = IM_Sby.getSelectedItem().toString();
        String key = IM_key.getText();

        IM_Stable.setModel(DbUtils.resultSetToTableModel(service.search_item(by,key)));

    }//GEN-LAST:event_IM_search_btnActionPerformed

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

        if( Iname == null || Iname.length() == 0 )
        {

            IM_U_name_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        }
        else
        {

            IM_U_name_error_lbl.setText("");

        }

        if(Itype == null || Itype.length() == 0)
        {

            IM_U_brand_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        }
        else
        {

            IM_U_brand_error_lbl.setText("");

        }

        if(ISprice == null || ISprice.length() == 0)
        {

            IM_U_Sprice_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        }
        else
        {

            IM_U_Sprice_error_lbl.setText("");

        }

        if(I_Uqty.getText() == null || I_Uqty.getText().length() == 0)
        {

            IM_U_qty_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        }
        else
        {

            Iqty =  Integer.parseInt(I_Uqty.getText()) ;
            IM_U_qty_error_lbl.setText("");
        }

        if(IBprice == null || IBprice.length() == 0 )
        {

            IM_U_Bprice_error_lbl.setText("*this field is empty");
            Uvalid = 1;

        }
        else
        {

            IM_U_Bprice_error_lbl.setText("");
        }

        if(Iqty < 0)
        {

            IM_U_qty_error_lbl.setText("*invalid qty");
            Uvalid = 1;

        }
        try
        {
            
        
        if(Double.parseDouble(IBprice)  < 0 )
        {

            IM_U_Bprice_error_lbl.setText("*invalid ammount");
            Uvalid = 1;

        }

        if(Double.parseDouble(ISprice)  < 0 )
        {

            IM_U_Sprice_error_lbl.setText("*invalid ammount");
            Uvalid = 1;

        }

        if(Uvalid == 0){

            service.update_items(ans,Iid,Iname,Itype,Iqty,IBprice,ISprice);
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

    private void IM_delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_delete_btnActionPerformed

        int ans = JOptionPane.showConfirmDialog(null, "are you sure you want to delete selected recode ?");

        IM_Service service = new IM_Service();

        String Iid = I_ID.getText();

        service.update_items(Iid,ans);
        IM_Utable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));
        IM_U_delete.setText("item deleted");
    }//GEN-LAST:event_IM_delete_btnActionPerformed

    private void IM_add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IM_add_btnActionPerformed

        IM_Service service = new IM_Service();

        int valid = 0;
        String Iname = I_name.getText();
        String Itype = I_type.getText();
        int Iqty = 0;
        double IBprice = 0;
        double ISprice = 0;

        if( Iname == null || Iname.length() == 0 )
        {

            IM_A_name_error_lbl.setText("*this field is empty");
            valid = 1;

        }
        else
        {

            IM_A_name_error_lbl.setText("");

        }

        if(Itype == null || Itype.length() == 0)
        {

            IM_A_brand_error_lbl.setText("*this field is empty");
            valid = 1;

        }
        else
        {

            IM_A_brand_error_lbl.setText("");

        }

        if(I_Sprice.getText() == null || I_Sprice.getText().length() == 0)
        {

            IM_A_Sprice_error_lbl.setText("*this field is empty");
            valid = 1;

        }
        else
        {
            ISprice = Double.parseDouble(I_Sprice.getText());
            IM_A_Sprice_error_lbl.setText("");

        }

        if(I_qty.getText() == null || I_qty.getText().length() == 0)
        {

            IM_A_qty_error_lbl.setText("*this field is empty");
            valid = 1;

        }
        else
        {

            Iqty = Integer.parseInt(I_qty.getText()) ;
            IM_A_qty_error_lbl.setText("");
        }

        if(I_Bprice.getText() == null || I_Bprice.getText().length() == 0 )
        {

            IM_A_Bprice_error_lbl.setText("*this field is empty");
            valid = 1;

        }
        else
        {
            IBprice = Double.parseDouble(I_Bprice.getText());
            IM_A_Bprice_error_lbl.setText("");
        }

        if(Iqty < 0)
        {

            IM_A_qty_error_lbl.setText("*invalid qty");
            valid = 1;

        }

        if(IBprice < 0 )
        {

            IM_A_Bprice_error_lbl.setText("*invalid ammount");
            valid = 1;

        }

        if(ISprice < 0 )
        {

            IM_A_Sprice_error_lbl.setText("*invalid ammount");
            valid = 1;

        }

        if(valid == 0){

            service.add_item(Iname,Itype,Iqty,IBprice,ISprice);
            IM_A_Bprice_error_lbl.setText("");
            IM_A_Sprice_error_lbl.setText("");
            IM_A_qty_error_lbl.setText("");
            IM_A_brand_error_lbl.setText("");
            IM_A_name_error_lbl.setText("");

            IM_Atable.setModel(DbUtils.resultSetToTableModel(IM_table.IM_tableload()));
            IM_A_success.setText("item added successfully");

        }

    }//GEN-LAST:event_IM_add_btnActionPerformed

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
    private javax.swing.JPanel DMjPanel9;
    private javax.swing.JPanel EMjPanel4;
    private javax.swing.JPanel FMjPanel11;
    private javax.swing.JLabel ID_Val;
    private javax.swing.JLabel ID_cus;
    private javax.swing.JLabel IM_A_Bprice_error_lbl;
    private javax.swing.JLabel IM_A_Sprice_error_lbl;
    private javax.swing.JLabel IM_A_brand_error_lbl;
    private javax.swing.JLabel IM_A_name_error_lbl;
    private javax.swing.JLabel IM_A_qty_error_lbl;
    private javax.swing.JLabel IM_A_success;
    private javax.swing.JTable IM_Atable;
    private javax.swing.JLabel IM_ID_lbl;
    private javax.swing.JComboBox IM_Sby;
    private javax.swing.JTable IM_Stable;
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
    private javax.swing.JButton IM_search;
    private javax.swing.JButton IM_search_btn;
    private javax.swing.JLabel IM_serch_by;
    private javax.swing.JLabel IM_sprice;
    private javax.swing.JLabel IM_sprice_lbl;
    private javax.swing.JLabel IM_type;
    private javax.swing.JLabel IM_type_lbl;
    private javax.swing.JButton IM_update_btn;
    private javax.swing.JButton IM_update_delete;
    private javax.swing.JPanel IMadd;
    private javax.swing.JPanel IMjPanel5;
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
    private javax.swing.JButton ManageCustomers;
    private javax.swing.JLabel PMjLabel6;
    private javax.swing.JPanel PMjPanel7;
    private javax.swing.JButton Reports;
    private javax.swing.JLabel SUJMjLabel15;
    private javax.swing.JLabel SUMMjLabel19;
    private javax.swing.JTextField SUMMjTextField3;
    private javax.swing.JButton SUMjButton10;
    private javax.swing.JButton SUMjButton9;
    private javax.swing.JComboBox SUMjComboBox1;
    private javax.swing.JPanel SUMjComboBox3;
    private javax.swing.JLabel SUMjLabel12;
    private javax.swing.JLabel SUMjLabel13;
    private javax.swing.JLabel SUMjLabel14;
    private javax.swing.JLabel SUMjLabel6;
    private javax.swing.JLabel SUMjLabel7;
    private javax.swing.JTextField SUMjTextField1;
    private javax.swing.JTextField SUMjTextField2;
    private javax.swing.JTextField SUMjTextField3;
    private javax.swing.JTextField SUMjTextField4;
    private javax.swing.JTextField SUaddressName;
    private javax.swing.JPanel SaMjPanel8;
    private javax.swing.JButton SuMAdd;
    private javax.swing.JButton SuMPlace;
    private javax.swing.JButton SuMReport;
    private javax.swing.JButton SuMUpdate;
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
    private javax.swing.JPanel attendanceEmpCard;
    private javax.swing.JButton attendanceEmpNavBtn;
    private java.util.List<model.Billpayment> billpaymentList;
    private javax.persistence.Query billpaymentQuery;
    private java.util.List<model.Billtype> billtypeList;
    private javax.persistence.Query billtypeQuery;
    private javax.swing.JButton btnBackGenerateBill1;
    private javax.swing.JButton btnChkCustId;
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
    private javax.swing.JTextField cusIdAdd;
    private javax.swing.JTextField cusNameAdd;
    private javax.swing.JTextField cusPhoneAdd;
    private javax.swing.JScrollPane cus_view_scroll;
    private javax.swing.JTable cus_view_table;
    private javax.swing.JTextField custp;
    private javax.swing.JComboBox<String> custpaymentmethod;
    private javax.swing.JButton deleteEmpBtn;
    private javax.swing.JButton deleteEmpTypeBtn;
    private javax.swing.JButton delete_cus_U;
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
    private javax.swing.JTextField empEndDateBox;
    private javax.swing.JTextField empEndDateBox_mgmt;
    private javax.swing.JTextField empEndDateBox_mgmt1;
    private javax.swing.JLabel empEndDateLabel;
    private javax.swing.JLabel empEndDateLabel_mgmt;
    private javax.swing.JLabel empEndDateLabel_mgmt1;
    private javax.swing.JTextField empID_mgmt1;
    private javax.swing.JTextField empIdBox;
    private javax.swing.JTextField empLeaveBox;
    private javax.swing.JPanel empLeaveCard;
    private javax.swing.JLabel empLeaveLabel;
    private javax.swing.JButton empLeaveMgmtNavBtn;
    private javax.swing.JPanel empLeaveMgmtPanel;
    private javax.swing.JLabel empLeaveReqIDLabel_mgmt;
    private javax.swing.JTextField empLeaveReqID_mgmt;
    private javax.swing.JButton empLeaveRequestBtn;
    private javax.swing.JTable empLeaveRequestTable;
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
    private javax.swing.JTextField empstartDateBox;
    private javax.swing.JTextField empstartDateBox_mgmt;
    private javax.swing.JLabel empstartDateLabel;
    private javax.swing.JLabel empstartDateLabel_mgmt;
    private javax.swing.JLabel empstartDateLabel_mgmt1;
    private javax.swing.JComboBox<String> emptypeComboBox;
    private javax.swing.JLabel head_add_cus;
    private javax.swing.JLabel id_cus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
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
    private javax.swing.JTextField phoneBoxCus;
    private javax.swing.JLabel phone_cus;
    private javax.swing.JLabel phone_cus_u;
    private javax.swing.JLabel phone_val;
    private javax.swing.JLabel phone_val_U1;
    private javax.swing.JPanel pmInsertBillPayments;
    private javax.swing.JPanel pmSearchBillPayments;
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
    private javax.swing.JButton smreset;
    private javax.swing.JLabel smsalesidreturns;
    private javax.swing.JTextField smsalesidreturnsbox;
    private javax.swing.JLabel smshowitemid;
    private javax.swing.JLabel smshowitemname;
    private javax.swing.JTextField smtotbillamt;
    private javax.swing.JTextField smtxtfieldenterbillid;
    private javax.swing.JTextField smtxtitemid;
    private javax.swing.JTextField smtxtitemname;
    private javax.swing.JTable smviewreturnstable;
    private javax.swing.JButton submit_cuss_add;
    private javax.swing.JPanel sumPanel16;
    private javax.swing.JButton updateEmpBtn;
    private javax.swing.JButton updateEmpTypeBtn;
    private javax.swing.JButton update_cus_u;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
