import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.*;
import javax.sound.sampled.*;

public class DespertadorVisual extends JFrame {
    public static JLabel lblDespertar;
    // public static JTextField txtHoraDespertar;
    public static JComboBox<String> cbxHoraDespertar;
    public static String[] horas = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    public static JLabel lblSeparadorDoisPontos;
    public static String strDoisPontos = ":";
    // public static JTextField txtMinutoDespertar;
    public static JComboBox<String> cbxMinutoDespertar;
    public static String[] minutos = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};

    public static JButton btnDefinirAlarme;
    public static boolean alarmeDefinido = false;

    public static int horaAtual;
    public static int horaRestante;
    public static int horaDespertar = -1;

    public static int minutoAtual;
    public static int minutoRestante;
    public static int minutoDespertar = -1;

    public static int segundoAtual;
    public static int segundoRestante;

    public static JLabel lblHorarioAtual;

    public static JCheckBox chkAtivarSoneca;
    public static JCheckBox chkVolumeCrescente;
    public static JCheckBox chkTempoRestante;

    public static JLabel lblTempoSoneca;
    public static JComboBox<String> cbxTempoSoneca;
    public static String[] strTempos = {"5", "10"};

    public static JLabel lblMensagemDespertador;
    public static JTextField txtMensagemDespertador;

    public static JLabel lblNotificacoes;

    public static GridBagLayout gbLayout;
    public static GridBagConstraints gbConstraints;

    public DespertadorVisual() {
        super("Despertador 1.0");
        gbLayout = new GridBagLayout();
        setLayout(gbLayout);
        gbConstraints = new GridBagConstraints();

        lblDespertar = new JLabel("Despertar às:");
        addComponent(lblDespertar, 0, 0, 3, 1);

        // txtHoraDespertar = new JTextField(10);
        cbxHoraDespertar = new JComboBox<String>(horas);
        addComponent(cbxHoraDespertar, 0, 3, 2, 1);

        lblSeparadorDoisPontos = new JLabel(strDoisPontos);
        addComponent(lblSeparadorDoisPontos, 0, 5, 1, 1);

        // txtMinutoDespertar = new JTextField(10);
        cbxMinutoDespertar = new JComboBox<String>(minutos);
        addComponent(cbxMinutoDespertar, 0, 6, 2, 1);

        btnDefinirAlarme = new JButton("Definir Alarme");
        addComponent(btnDefinirAlarme, 1, 0, 8, 1);

        lblHorarioAtual = new JLabel("", SwingConstants.CENTER);
        addComponent(lblHorarioAtual, 2, 0, 8, 1);

        chkAtivarSoneca = new JCheckBox("Ativar Soneca?");
        addComponent(chkAtivarSoneca, 3, 0, 2, 1);

        chkVolumeCrescente = new JCheckBox("Volume Crescente?");
        addComponent(chkVolumeCrescente, 3, 2, 2, 1);
        
        chkTempoRestante = new JCheckBox("Tempo Restante");
        addComponent(chkTempoRestante, 3, 4, 4, 1);

        lblTempoSoneca = new JLabel("Tempo Soneca:");
        addComponent(lblTempoSoneca, 4, 0, 3, 1);

        cbxTempoSoneca = new JComboBox<String>(strTempos);
        addComponent(cbxTempoSoneca, 4, 4, 5, 1);

        lblMensagemDespertador = new JLabel("Mensagem do Despertador:");
        addComponent(lblMensagemDespertador, 5, 0, 4, 1);

        txtMensagemDespertador = new JTextField(10);
        addComponent(txtMensagemDespertador, 5, 5, 4, 1);

        lblNotificacoes = new JLabel("Notificações", SwingConstants.CENTER);
        addComponent(lblNotificacoes, 6, 0, 8, 1);

        btnDefinirAlarme.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    String strHoraDespertar = String.valueOf(cbxHoraDespertar.getSelectedItem()).trim();
                    String strMinutoDespertar = String.valueOf(cbxMinutoDespertar.getSelectedItem()).trim();

                    if (strHoraDespertar.equals("")) {
                        notificarUsuario("É necessário digitar um número inteiro para a hora do alarme.");
                        cbxHoraDespertar.requestFocus();
                        alarmeDefinido = false;
                        return;
                    }
                    
                    if (strMinutoDespertar.equals("")) {
                        notificarUsuario("É necessário digitar um número inteiro para minuto do alarme.");
                        cbxMinutoDespertar.requestFocus();
                        alarmeDefinido = false;
                        return;
                    }

                    horaDespertar = Integer.valueOf(String.valueOf(cbxHoraDespertar.getSelectedItem()));
                    minutoDespertar = Integer.valueOf(String.valueOf(cbxMinutoDespertar.getSelectedItem()));
                    alarmeDefinido = true;
                    notificarUsuario("Alarme definido para: " + strHoraDespertar + "h : " + strMinutoDespertar + "m");
                }
            }
        );

        // txtHoraDespertar.addKeyListener(
        //     new KeyAdapter() {
        //         @Override
        //         public void keyReleased(KeyEvent event) {
        //             txtHoraDespertar.setText(somenteNumeros(txtHoraDespertar.getText()));
        //         }
        //     }
        // );

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 250);
        setVisible(true);
    }

    public static void dormir(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (Exception e) {
            System.err.println("Erro: " + e);
        }
    }

    public static void definirHorarioAtual() {
        horaAtual = LocalDateTime.now().getHour();
        minutoAtual = LocalDateTime.now().getMinute();
        segundoAtual = LocalDateTime.now().getSecond();
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
        int espacamento = 3;
        gbConstraints.insets = new Insets(espacamento, espacamento, espacamento, espacamento);
        gbConstraints.insets = new Insets(espacamento, espacamento, espacamento, espacamento);
        gbLayout.setConstraints(component, gbConstraints);
        add(component);
    }

    public static void notificarUsuario(String str) {
        lblNotificacoes.setText(setHtmlFormat(str));
    }

    public static String setHtmlFormat(String str) {
        return "<html><body>" + str + "</body></html>";
    }

    public static void verificarLarguraEAltura() { // checkFrameWidthHeight()
        appDespertadorVisual.getRootPane().addComponentListener(
            new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    int larguraTela = appDespertadorVisual.getWidth();
                    int alturaTela = appDespertadorVisual.getHeight();
                    notificarUsuario(String.format("Largura: %s, Altura: %s", larguraTela, alturaTela));
                }
            }
        );
    }

    public static String somenteNumeros(String strTexto) {
        strTexto = strTexto.trim();
        String strFormatada = "";
        for (int i = 0; i < strTexto.length(); i++) {
            try {
                Double.parseDouble(String.valueOf(strTexto.charAt(i)));
                strFormatada += String.valueOf(strTexto.charAt(i));
            } catch (Exception e) {
                System.err.println("Erro: " + e);
            }
        }
        return strFormatada;
    }

    public static void tempoRestante() {
        try {
            horaDespertar = Integer.valueOf(String.valueOf(cbxHoraDespertar.getSelectedItem()).trim());
            minutoDespertar = Integer.valueOf(String.valueOf(cbxMinutoDespertar.getSelectedItem()).trim());
        } catch (Exception e) {
            // System.err.println("Erro: " + e);
        }

        if (horaDespertar >= 0 && minutoDespertar >= 0 && chkTempoRestante.isSelected() == true) {
            if (horaAtual > 0) {
                horaRestante = (24 - horaAtual) + horaDespertar;
            } else {
                horaRestante = horaDespertar;
            }

            if (minutoAtual > 0) {
                minutoRestante = (60 - minutoAtual) + minutoDespertar;
            } else {
                minutoRestante = minutoDespertar;
            }

            if (segundoAtual > 0) {
                segundoRestante = 60 - segundoAtual;
            } else {
                segundoRestante = segundoAtual;
            }

            if (segundoRestante > 59) {
                segundoRestante = 0;
            }

            notificarUsuario(String.format("Faltam %s horas, %s minutos e %s segundos para o próximo alarme.", horaRestante, minutoRestante, segundoRestante));
        }
    }

    public static void tocarAlarme() {
        if (horaAtual == horaDespertar && minutoAtual == minutoDespertar && alarmeDefinido == true) {
            tocarSom(chkAtivarSoneca.isSelected());
            notificarUsuario("O despertador: " + txtMensagemDespertador.getText() + " está ativo.");
            if (chkAtivarSoneca.isSelected()) {
                if (JOptionPane.showConfirmDialog(null, "Deseja adiar o alarme?") == 0) {
                    minutoDespertar = Integer.valueOf(String.valueOf(cbxTempoSoneca.getSelectedItem()));
                } else {
                    alarmeDefinido = false;
                }
            }
        }
    }

    public static synchronized void tocarSom(boolean volumeCrescente) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Despertador.class.getResourceAsStream("./see-you-later-203103.wav"));
                    clip.open(inputStream);
                    clip.start();

                    if (volumeCrescente == true) {
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        float volumeMinimo = -30.0f;
                        float volumeMaximo = 0.0f;
                        float volumeAumentar = 6.0f;
                        gainControl.setValue(volumeMinimo); // Reduce volume by 10 decibels.
                        boolean aumentarVolume = true;

                        long clipTime;
                        while (aumentarVolume == true) {
                            if (gainControl.getValue() >= volumeMinimo && gainControl.getValue() <= volumeMaximo) {
                                clipTime = clip.getMicrosecondPosition();
                                clip.stop();
                                gainControl.setValue(gainControl.getValue() + volumeAumentar);
                                clip.setMicrosecondPosition(clipTime);
                                clip.start();
                                try {
                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                    System.err.println("Erro: " + e);
                                }
                                // System.out.println("clip.getMicrosecondPosition(): " + clip.getMicrosecondPosition());
                            } else {
                                aumentarVolume = false;
                            }
                            System.out.println("gainControl.getValue(): " + gainControl.getValue());
                        }
                    } else {
                        clip.stop();
                        clip.start();
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public static DespertadorVisual appDespertadorVisual;
    public static void main(String[] args) {
        appDespertadorVisual = new DespertadorVisual();
        verificarLarguraEAltura();
        while (true) {
            definirHorarioAtual();
            lblHorarioAtual.setText("Agora são: " + horaAtual + "h : " + minutoAtual + "m : " + segundoAtual + "s");
            tempoRestante();
            tocarAlarme();
            dormir(1000);
        }
    }
}
