import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class TelaDeRemover extends JFrame {
    public static JLabel lblId;
    public static JComboBox<String> cbxId;

    public static JLabel lblNome;
    public static JTextField txtNome;
    
    public static JLabel lblEmail;
    public static JTextField txtEmail;
   
  
    public static JButton btnRemover;
    public static JButton btnCancelar;

    public static JLabel lblNotificacoes;

    public static GridBagLayout gbLayout;
    public static GridBagConstraints gbConstraints;

    public TelaDeRemover() {
        super("Tela de Remover");

        gbLayout = new GridBagLayout();
        setLayout(gbLayout);
        gbConstraints = new GridBagConstraints();

        lblId = new JLabel("Id:", SwingConstants.RIGHT);
        addComponent(lblId, 0, 0, 1, 1);

        cbxId = new JComboBox<String>();
        popularCbxId();
        addComponent(cbxId, 0, 1, 1, 1);

        lblNome = new JLabel("Nome:", SwingConstants.RIGHT);
        addComponent(lblNome, 1, 0, 1, 1);

        txtNome = new JTextField(10);
        txtNome.setEditable(false);
        addComponent(txtNome, 1, 1, 1, 1);

        lblEmail = new JLabel("Email:", SwingConstants.RIGHT);
        addComponent(lblEmail, 2, 0, 1, 1);

        txtEmail = new JTextField(10);
        txtEmail.setEditable(false);
        addComponent(txtEmail, 2, 1, 1, 1);

        atualizarCampos(String.valueOf(cbxId.getSelectedItem()));

        btnRemover = new JButton("Remover");
        addComponent(btnRemover,4,0,1,1);
     
        btnCancelar = new JButton("Cancelar");
        addComponent(btnCancelar, 4, 1, 1, 1);

        lblNotificacoes = new JLabel("Notificações", SwingConstants.CENTER);
        addComponent(lblNotificacoes, 5, 0, 2, 1);

        cbxId.addItemListener(
            new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent event) {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        atualizarCampos(String.valueOf(cbxId.getSelectedItem()));
                    }
                }
            }
        );

        // cbxId.addActionListener(
        //     new ActionListener() {
        //         @Override
        //         public void actionPerformed(ActionEvent event) {
        //             if (!txtNome.getText().trim().equals(txtNomeCarregado.trim()) && JOptionPane.showConfirmDialog(null, "Nome modificado! Deseja alternar para outro id?") == JOptionPane.CANCEL_OPTION) {
        //                 return;
        //             }
        //             System.out.println("aqui ok");
        //         }
        //     }
        // );

        btnRemover.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    try {
                        Connection conexao = MySQLConnector.conectar();
                        String strSqlRemoverId = "delete from `login`.`tbl_login` where `id` = " + String.valueOf(cbxId.getSelectedItem()) + ";";
                        Statement stmSqlRemoverId = conexao.createStatement();
                        stmSqlRemoverId.addBatch(strSqlRemoverId);
                        stmSqlRemoverId.executeBatch();
                        notificarUsuario("O id " + String.valueOf(cbxId.getSelectedItem()) + " foi atualizado com sucesso.");
                    } catch (Exception e) {
                        notificarUsuario("Ops! Problema no servidor, tente novamente mais tarde.");
                        System.err.println("Erro: " + e);
                    }

                    try {
                        cbxId.setSelectedIndex(cbxId.getSelectedIndex() + 1);
                        cbxId.removeItemAt(cbxId.getSelectedIndex() - 1);
                    } catch (Exception e) {
                        cbxId.setSelectedIndex(cbxId.getSelectedIndex() - 1);
                        cbxId.removeItemAt(cbxId.getSelectedIndex() + 1);
                    }
                    atualizarCampos(String.valueOf(cbxId.getSelectedItem()));
                }
            }
        );

        btnCancelar.addActionListener (
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    if (JOptionPane.showConfirmDialog(null, "Deseja cancelar e sair da Tela de Remover") == 0) {
                        System.exit(1);
                    }
                }
            }
        );

        setSize(1980,720);
        setVisible(true);
    }

    public void addComponent(Component component, int row, int column, int width, int height) {
        if (height > 1 && width > 1) {
            gbConstraints.fill = GridBagConstraints.BOTH;
        } else if (height > 1) {
            gbConstraints.fill = GridBagConstraints.VERTICAL;
        } else {
            gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        }
        gbConstraints.gridy = row;
        gbConstraints.gridx = column;
        gbConstraints.gridwidth = width;
        gbConstraints.gridheight = height;
        gbLayout.setConstraints(component, gbConstraints);
        add(component);
    }

    public static void popularCbxId() {
        try {
            Connection conexao = MySQLConnector.conectar();
            String strSqlPopularCbxId = "select * from `login`.`tbl_login` order by `id` asc;";
            Statement stmSqlPopularCbxId = conexao.createStatement();
            ResultSet rstSqlPopularCbxId = stmSqlPopularCbxId.executeQuery(strSqlPopularCbxId);
            while (rstSqlPopularCbxId.next()) {
                cbxId.addItem(rstSqlPopularCbxId.getString("id"));
            }
            stmSqlPopularCbxId.close();
        } catch (Exception e) {
            lblNotificacoes.setText("Ops! Ocorreu um problema no servidor e não será possível carregar os ids neste momento. Por favor, retorne novamente mais tarde.");
            System.err.println("Erro: " + e);
        }
    }

    public static void notificarUsuario(String str) {
        lblNotificacoes.setText(setHtmlFormat(str));
    }

    public static String setHtmlFormat(String str) {
        return "<html><body>" + str + "</body></html>";
    }

    public static void atualizarCampos(String strId) {
        try {
            Connection conexao = MySQLConnector.conectar();
            String strSqlAtualizarCampos = "select * from `login`.`tbl_login` where id = " + strId + ";";
            Statement stmSqlAtualizarCampos = conexao.createStatement();
            ResultSet rstSqlAtualizarCampos = stmSqlAtualizarCampos.executeQuery(strSqlAtualizarCampos);
            if (rstSqlAtualizarCampos.next()) {
                txtNome.setText(rstSqlAtualizarCampos.getString("nome"));
               
            } else {
                notificarUsuario("Id não encontrado.");
            }
        } catch (Exception e) {
            notificarUsuario("Ops! Problema no servidor. Tente novamente mais tarde.");
            System.err.println("Erro: " + e);
        }
    };

    public static void verificarLarguraEAltura()  {
    appTelaDeRemover.getRootPane().addComponentListener(
             new ComponentAdapter() {
                 public void componentResized(ComponentEvent e) {
                     int larguraTela = appTelaDeRemover.getWidth();
                     int alturaTela = appTelaDeRemover.getHeight();
                     notificarUsuario(String.format("Largura: %s, Altura: %s", larguraTela, alturaTela));
                 }
             }
         );
    }

    public static TelaDeRemover appTelaDeRemover;
    public static void main(String[] args) {
        appTelaDeRemover = new TelaDeRemover();
        appTelaDeRemover.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}