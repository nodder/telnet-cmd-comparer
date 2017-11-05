package cdd.product.zte.telnetcompare.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import name.cdd.toolkit.swing.util.SwingUtil;
import cdd.product.zte.telnetcompare.common.TelnetInfo;
import cdd.product.zte.telnetcompare.common.TelnetTemplate;
import cdd.product.zte.telnetcompare.common.VersionConst;

public class TelnetCompareFrm extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private NePanel nePanel = new NePanel();
    private TaskPanel taskPanel = new TaskPanel();
    
    private Presenter presenter = new Presenter();
    
    public TelnetCompareFrm()
    {
        jbInit();
        init();
    }

    private void jbInit()
    {
        this.setTitle("Telnet Command Comparer " + VersionConst.currVersion + "               - cdd");
        this.setSize(new Dimension(500, 340));
        
        JPanel pnParent = new JPanel();
        JPanel pnNorth = new JPanel();
        
        this.getContentPane().add(pnParent);
        
        BorderLayout bl = new BorderLayout();
        pnParent.setLayout(bl);
        
        BorderLayout b2 = new BorderLayout();
        pnNorth.setLayout(b2);
        
        pnParent.add(pnNorth, BorderLayout.NORTH);
        
        pnNorth.add(nePanel, BorderLayout.NORTH);
        pnNorth.add(taskPanel, BorderLayout.CENTER);
        
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(WindowEvent e)//关闭窗口时退出javaw
            {
                System.exit(1);
            }
        });

        SwingUtil.centerWindow(this);
    }
    
    private void addLable(JPanel mainPanel, JComponent com, int gridx, int gridy, Insets insets)
    {
        mainPanel.add(com, new GridBagConstraints(gridx, gridy, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, 
            GridBagConstraints.NONE, insets, 0, 0));
    }
    
    private void addTextField(JPanel mainPanel, JComponent com, int gridx, int gridy, Insets insets)
    {
        mainPanel.add(com, new GridBagConstraints(gridx, gridy, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, 
            GridBagConstraints.HORIZONTAL, insets, 0, 0));
    }
    
    private void init()
    {
        this.taskPanel.clearFile();
        
        try
        {
            TelnetInfo telnetInfo = this.presenter.getParams();
            this.nePanel.initParams(telnetInfo);
        }
        catch(Exception e1)
        {
            JOptionPane.showMessageDialog(this, e1.getMessage());
        }
    }
    
    private void snapshot(File outputFile) throws IOException
    {
        this.presenter.snapshot(outputFile, this.nePanel.getUIParams());
    }
    
    private void compare(File output1, File output2)
    {
        this.presenter.compareSnapshot(output1, output2);
    }
    
    private void saveParams() throws IOException
    {
        this.presenter.saveParams(this.nePanel.getUIParams());
    }
    
    private String refreshPromptSnmp(String ipAddr) throws Exception
    {
        return this.presenter.refreshPromptSnmp(ipAddr);
    }
    
    private class NePanel extends JPanel
    {
        private static final long serialVersionUID = 1L;
        
        private final Map<String, TelnetInfo> templateMap = TelnetTemplate.getTemplateMap();

        public NePanel()
        {
            GridLayout gl = new GridLayout();
            gl.setColumns(2);
            this.setLayout(gl);
            this.add(pnLeft);
            this.add(pnRight);
            
            this.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(148, 145, 140)), "DSLAM Parameters"),
                BorderFactory.createEmptyBorder(0, 0, 6, 0)));
            
            pnLeft.setLayout(new GridBagLayout());
            pnRight.setLayout(new GridBagLayout());
            
            addLable(pnLeft, lblIpAddress, 0, 0, new Insets(6, 6, 0, 0));
            addTextField(pnLeft, txtIpAddress, 1, 0, new Insets(6, 6, 0, 6));
            addLable(pnLeft, lblLoginPrompt, 0, 1, new Insets(6, 6, 0, 0));
            addTextField(pnLeft, txtLoginPrompt, 1, 1, new Insets(6, 6, 0, 6));
            addLable(pnLeft, lblPasswordPrompt, 0, 2, new Insets(6, 6, 0, 0));
            addTextField(pnLeft, txtPasswordPrompt, 1, 2, new Insets(6, 6, 0, 6));
            addLable(pnLeft, lblprompt, 0, 3, new Insets(6, 6, 0, 0));
            addTextField(pnLeft, pnPrompt, 1, 3, new Insets(6, 6, 0, 6));
            
            addLable(pnRight, lblImport, 0, 0, new Insets(6, 6, 0, 0));
            addTextField(pnRight, cmbImport, 1, 0, new Insets(6, 6, 0, 6));
            addLable(pnRight, lblUserName, 0, 1, new Insets(6, 6, 0, 0));
            addTextField(pnRight, txtUserName, 1, 1, new Insets(6, 6, 0, 6));
            addLable(pnRight, lblPassword, 0, 2, new Insets(6, 6, 0, 0));
            addTextField(pnRight, txtPassword, 1, 2, new Insets(6, 6, 0, 6));
            addLable(pnRight, lblSplitPage, 0, 3, new Insets(6, 6, 0, 0));
            addTextField(pnRight, txtSplitPage, 1, 3, new Insets(6, 6, 0, 6));
            
            BorderLayout blPrompt = new BorderLayout();
            blPrompt.setHgap(6);
            pnPrompt.setLayout(blPrompt);
            
            pnPrompt.add(btnRefreshPrompt, BorderLayout.WEST);
            pnPrompt.add(txtprompt, BorderLayout.CENTER);
            
            btnRefreshPrompt.setPreferredSize(new Dimension(20, 15));
            btnRefreshPrompt.setFont(new Font("arial", Font.PLAIN, 11));
            btnRefreshPrompt.setMargin(new Insets(0, 0, 0, 0));
            btnRefreshPrompt.setText("э");
//            btnRefreshPrompt.setIcon(new ImageIcon("D:/Tools/TelnetCmdCompareV11/icon.JPG"));
            
            btnRefreshPrompt.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    btnRefreshPrompt_actionPerformed(e);
                }
            });
            
            cmbImport.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    cmbImport_actionPerformed(e);
                }
            });
            
            init();
        }
        
        private void init()
        {
            this.cmbImport.removeAllItems();
            this.cmbImport.addItem("Self-defined");
            
            Iterator<Entry<String, TelnetInfo>> it = templateMap.entrySet().iterator();
            while(it.hasNext())
            {
                Entry<String, TelnetInfo> item = it.next();
                this.cmbImport.addItem(item.getKey());
            }
        }

        private void btnRefreshPrompt_actionPerformed(ActionEvent e)
        {
            String ipAddr = txtIpAddress.getText();
            if(ipAddr == null)
            {
                JOptionPane.showMessageDialog(this, "Please input IP address");
                txtIpAddress.grabFocus();
                return;
            }
            
            try
            {
                String prompt = refreshPromptSnmp(ipAddr);
                txtprompt.setText(prompt);
            }
            catch(Exception e1)
            {
                JOptionPane.showMessageDialog(this, "Cannot refresh prompt.\nDetails:" + e1.getMessage());
            }
        }

        private void cmbImport_actionPerformed(ActionEvent e)
        {
            String templateName = cmbImport.getSelectedItem().toString();
            
            if(isTemplate(templateName))
            {
                initParams(templateMap.get(templateName));
                enableUI(false);
            }
            else
            {
                enableUI(true);
            }
        }

        private boolean isTemplate(String templateName)
        {
            return templateMap.containsKey(templateName);
        }
        
        private void enableUI(boolean en)
        {
            txtSplitPage.setEditable(en);
            txtPassword.setEditable(en);
            txtLoginPrompt.setEditable(en);
            txtUserName.setEditable(en);
            txtPasswordPrompt.setEditable(en);
        }

        public void initParams(TelnetInfo info)
        {
            if(info != null)
            {
                if(info.ipAddr != null)
                {
                    txtIpAddress.setText(info.ipAddr);
                }
                
                txtSplitPage.setText(info.pageSplitString);
                txtprompt.setText(info.prompt);
                txtPassword.setText(info.loginPasswd);
                txtLoginPrompt.setText(info.loginPrompt);
                txtUserName.setText(info.loginUserName);
                txtPasswordPrompt.setText(info.passwdPrompt);
            }
        }

        public TelnetInfo getUIParams()
        {
            TelnetInfo info = new TelnetInfo();
            info.pageSplitString = txtSplitPage.getText();
            info.prompt = txtprompt.getText();
            info.loginPasswd = txtPassword.getText();
            info.loginPrompt = txtLoginPrompt.getText();
            info.loginUserName = txtUserName.getText();
            info.passwdPrompt = txtPasswordPrompt.getText();
            
            String ip = txtIpAddress.getText().trim();
            txtIpAddress.setText(ip);
            info.ipAddr = ip;
            
            return info;
        }
        
        private JPanel pnLeft = new JPanel();
        private JPanel pnRight = new JPanel();
        
        private JLabel lblIpAddress = new JLabel("IP Address:");
        private JLabel lblImport = new JLabel("Import template:");
        private JLabel lblLoginPrompt = new JLabel("Login prompt:");
        private JLabel lblUserName = new JLabel("Login name:");
        private JLabel lblPasswordPrompt = new JLabel("Password prompt:");
        private JLabel lblPassword = new JLabel("Password:");
        private JLabel lblSplitPage = new JLabel("Page split string:");
        
        private JTextField txtIpAddress = new JTextField();
        private JComboBox cmbImport = new JComboBox();
        private JTextField txtLoginPrompt = new JTextField();
        private JTextField txtUserName = new JTextField();
        private JTextField txtPasswordPrompt = new JTextField();
        private JTextField txtPassword = new JTextField();
        private JTextField txtSplitPage = new JTextField();
        
        private JLabel lblprompt = new JLabel("Prompt:");
        private JPanel pnPrompt = new JPanel();
        private JButton btnRefreshPrompt = new JButton("c");
        private JTextField txtprompt = new JTextField();
    }
    
    private class TaskPanel extends JPanel
    {
        private static final long serialVersionUID = 1L;
        private final File txtA = new File("./snapshotA.txt");
        private final File txtB = new File("./snapshotB.txt");
        
        public TaskPanel()
        {
            this.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(148, 145, 140)), "Task"),
                BorderFactory.createEmptyBorder(0, 0, 6, 0)));
            
            this.add(pnNorth, BorderLayout.NORTH);
            this.add(pnSouth, BorderLayout.SOUTH);
            
            pnSouth.add(btnCompare);
            
            pnNorth.setLayout(new BorderLayout());
            pnNorth.add(lblSnapshotA, BorderLayout.WEST);
            pnNorth.add(lblSnapshotA, BorderLayout.WEST);
            
            GridLayout blNorth = new GridLayout();
            blNorth.setColumns(2);
            blNorth.setRows(2);
            pnNorth.setLayout(blNorth);
            
            
            pnNorth.add(pnNorthLblA);
            pnNorth.add(pnNorthA);
            
            pnNorth.add(pnNorthLblB);
            pnNorth.add(pnNorthB);
            
            pnNorthLblA.add(lblSnapshotA);
            pnNorthLblB.add(lblSnapshotB);
            
            FlowLayout f1 = new FlowLayout();
            f1.setAlignment(FlowLayout.LEFT);
            pnNorthA.setLayout(f1);
            pnNorthA.add(btnSnapshotA);
            pnNorthA.add(lblSnapResultA);
            
            FlowLayout f2 = new FlowLayout();
            f2.setAlignment(FlowLayout.LEFT);
            pnNorthB.setLayout(f2);
            pnNorthB.add(btnSnapshotB);
            pnNorthB.add(lblSnapResultB);
            
            btnSnapshotA.setPreferredSize(new Dimension(83, 23));
            btnSnapshotB.setPreferredSize(new Dimension(83, 23));
            btnCompare.setPreferredSize(new Dimension(123, 33));
            
            btnSnapshotA.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    btnSnapshotA_actionPerformed(e);
                }
            });
            
            btnSnapshotB.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    btnSnapshotB_actionPerformed(e);
                }
            });
            
            btnCompare.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    btnCompare_actionPerformed(e);
                }
            });
        }
        
        protected void btnCompare_actionPerformed(ActionEvent e)
        {
            if(this.lblSnapResultA.getText().equals("OK") && this.lblSnapResultB.getText().equals("OK"))
            {
                compare(txtA, txtB);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Cannot compare");
            }
        }

        protected void btnSnapshotA_actionPerformed(ActionEvent e)
        {
            //TODO
            this.lblSnapResultA.setText("Please wait...");
            this.updateUI();
            
            try
            {
                saveParams();
                snapshot(txtA);
                this.lblSnapResultA.setText("OK");
            }
            catch(Exception e1)
            {
                this.lblSnapResultA.setText("error");
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
            
        }
        
        protected void btnSnapshotB_actionPerformed(ActionEvent e)
        {
            //TODO
            this.lblSnapResultB.setText("Please wait...");
            this.updateUI();
            
            try
            {
                saveParams();
                snapshot(txtB);
                this.lblSnapResultB.setText("OK");
            }
            catch(Exception e1)
            {
                this.lblSnapResultB.setText("error");
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
        }
        
        public void clearFile()
        {   
            txtA.delete();
            txtB.delete();
        }

        private JPanel pnNorth = new JPanel();
        private JPanel pnSouth = new JPanel();
        
        private JPanel pnNorthLblA = new JPanel();
        private JLabel lblSnapshotA = new JLabel("Snapshot A:");
        private JPanel pnNorthLblB = new JPanel();
        private JLabel lblSnapshotB = new JLabel("Snapshot B:");
        
        private JPanel pnNorthA = new JPanel();
        private JButton btnSnapshotA = new JButton("Shoot");
        private JButton btnSnapshotB = new JButton("Shoot");
        
        private JPanel pnNorthB= new JPanel();
        private JLabel lblSnapResultA = new JLabel();
        private JLabel lblSnapResultB = new JLabel();
        
        private JButton btnCompare = new JButton("Compare");
    }
}
