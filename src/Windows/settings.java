package Windows;

import Data.Cloudflare_API;
import Data.Domain;
import Data.Memory;
import Data.Record;
import Events.DoubleClick;
import Events.selectAllOnClick;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Created by Yosef on 04/10/2014.
 */
public class settings extends JPanel {
    public JPanel panel;
    private JTextField usernameTextField;
    private JTextField tokenTextField;
    private JButton obtainInfoButton;
    private JTree Domains;
    private Map<String, Domain> domains = new HashMap<String, Domain>();

    public settings() {

        usernameTextField.setText(Memory.getInstance().getUsername());
        usernameTextField.addMouseListener(new selectAllOnClick());
        tokenTextField.setText(Memory.getInstance().getToken());
        tokenTextField.addMouseListener(new selectAllOnClick());
        obtainInfoButton.addMouseListener(new obtainInfo());
        refreshTree();

    }


    public void refreshTree() {


        Memory.getInstance().setUsername(usernameTextField.getText());
        Memory.getInstance().setToken(tokenTextField.getText());


        DefaultTreeModel model = (DefaultTreeModel) Domains.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        //create the root node
        root.setUserObject("Domains");
        root.removeAllChildren();
        //create the child nodes
        model.nodeChanged(root);
        Domains.setModel(new JTree(root).getModel());
        Domains.addMouseListener(new EditDomainDClick());
        Domains.addKeyListener(new EditDomainKey());

        try {

            if (Memory.getInstance().isValidated())
                addDomains(new Cloudflare_API().getDomains());
        } catch (Exception e) {
        }

        Domains.expandRow(0);

        SplashScreen.hideSplash();

    }

    private void DeleteActions() {
        TreePath tp = Domains.getSelectionPath();
        if (tp != null)
            if (tp.getPathCount() == 4 || tp.getPathCount() == 3) {
                new RecordDeleter(domains.get(tp.getPathComponent(1).toString()).getRecords().get(Integer.parseInt(tp.getPathComponent(2).toString().replace("Record ", "")) - 1)).getRecord();
                refreshTree();
                expandTo(tp);
            }
    }

    public void EditingActions() {
        EditingActions(Domains.getSelectionPath());
    }

    public void EditingActions(TreePath tp) {
        int beforePath = 0;
        if (tp != null) {
            //  System.out.println(tp.getPathCount());
            //Editing a record
            if (tp.getPathCount() == 4 || tp.getPathCount() == 3) {
                Record r = domains.get(tp.getPathComponent(1).toString()).getRecords().get(Integer.parseInt(tp.getPathComponent(2).toString().replace("Record ", "")) - 1);
                r = new RecordEditor(r).getRecord();
                //  System.out.println(r.getName()+ " " + r.getValue() + " " + r.getType());
                refreshTree();
                expandTo(tp);
            }
            //Adding a new record
            else if (tp.getPathCount() == 2) {
                //  System.out.println("Adding a new record for"+tp.getLastPathComponent().toString());
                Record r = new RecordCreator(new Record(tp.getLastPathComponent().toString())).getRecord();
                System.out.println(r.getName() + " " + r.getValue() + " " + r.getType());
                refreshTree();

                expandTo(tp);
                expandRow(r.zone);

            }
        }


    }

    public void expandTo(TreePath tp) {

        List<TreePath> tps = new ArrayList<TreePath>();
        TreePath ntp = tp;
        for (int loop = 0, pcnt = tp.getPathCount() - 1; loop < pcnt; loop++)
            tps.add(ntp = ntp.getParentPath());
        Collections.reverse(tps);

        for (TreePath path : tps)
            expandRow(path);

    }

    public void expandRow(Object path) {
        for (int loop = 0, length = Domains.getRowCount(); loop < length; loop++)
            if (Domains.getPathForRow(loop).toString().equals(path.toString()))
                Domains.expandRow(loop);
    }

    public DefaultMutableTreeNode addTreeNode(DefaultMutableTreeNode node) {

        DefaultTreeModel model = (DefaultTreeModel) Domains.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.add(node);
        model.nodeChanged(root);
        return node;
    }

    public void addDomain(Domain domain) {
        DefaultMutableTreeNode zone = new DefaultMutableTreeNode(domain.name);
        domains.put(domain.name, domain);
        int recordscnt = 1;
        for (Record r : domain.getRecords()) {
            DefaultMutableTreeNode rec = new DefaultMutableTreeNode("Record " + recordscnt++);
            rec.add(new DefaultMutableTreeNode("name:" + r.getName()));
            rec.add(new DefaultMutableTreeNode("type:" + r.getType()));
            rec.add(new DefaultMutableTreeNode("value:" + r.getValue()));
            zone.add(rec);
        }
        addTreeNode(zone);

    }

    public void addDomains(List<Domain> domains) {
        for (Domain domain : domains)
            addDomain(domain);
    }

    public class EditDomainDClick extends DoubleClick {
        @Override
        public void doubleClick(MouseEvent e) {
            if (Domains.getLastSelectedPathComponent() != null)
                if (((TreeNode) Domains.getLastSelectedPathComponent()).isLeaf())
                    EditingActions();
        }


    }

    private class EditDomainKey extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 32:
                case 10:
                    EditingActions();
                    break;
                case 127:
                    DeleteActions();
                    break;


            }
            ;


        }
    }

    private class obtainInfo extends MouseAdapter {


        @Override
        public void mouseClicked(MouseEvent e) {
            refreshTree();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            SplashScreen.showSplash();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }
    }


}
