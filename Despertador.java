import java.time.*;
import java.util.*;
import javax.sound.sampled.*;

public class Despertador {
    public static void main(String[] args) {
        int horaAtual = LocalDateTime.now().getHour();
        int horaDespertar = -1;
        int horaRestante = -1;

        int minutoAtual = LocalDateTime.now().getMinute();
        int minutoDespertar = -1;
        int minutoRestante = -1;

        int segundoAtual = LocalDateTime.now().getSecond();
        int segundoRestante = -1;

        boolean adiarDespertador = false;
        int tempoMaximoSoneca = 10;
        boolean volumeCrescente = false;
        boolean encerrarDespertador = false;
        boolean respostaErrada = true;

        String respostaUsuario;

        String nomeDespertador;

        Scanner inputScanner = new Scanner(System.in);

        while (respostaErrada == true) {
            clearScreen();

            horaAtual = LocalDateTime.now().getHour();
            minutoAtual = LocalDateTime.now().getMinute();
            segundoAtual = LocalDateTime.now().getSecond();

            System.out.println("Agora são: " + horaAtual + "h:" + minutoAtual + "m:" + segundoAtual + "s");
            System.out.println("Digite abaixo SOMENTE NÚMERO INTEIRO (de 0 a 23) a hora do despertador e tecle enter:");
            respostaUsuario = inputScanner.nextLine().trim();
            try {
                horaDespertar = Integer.valueOf(respostaUsuario);
                respostaErrada = false;
            } catch (Exception e) {
                // System.err.println("Erro: " + e);
                System.out.println("Resposta inválida! Verifique sua digitação e tente novamente.");
                try {
                    Thread.sleep(5000);
                } catch (Exception ev) {
                    System.err.println("Erro: " + ev);
                }
            }
        }

        respostaErrada = true;
        while (respostaErrada == true) {
            clearScreen();

            horaAtual = LocalDateTime.now().getHour();
            minutoAtual = LocalDateTime.now().getMinute();
            segundoAtual = LocalDateTime.now().getSecond();

            System.out.println("Agora são: " + horaAtual + "h:" + minutoAtual + "m:" + segundoAtual + "s");
            System.out.println("Digite abaixo SOMENTE NÚMERO INTEIRO (de 0 a 59) o minuto do despertador e tecle enter:");
            respostaUsuario = inputScanner.nextLine().trim();
            try {
                minutoDespertar = Integer.valueOf(respostaUsuario);
                respostaErrada = false;
            } catch (Exception e) {
                // System.err.println("Erro: " + e);
                System.out.println("Resposta inválida! Verifique sua digitação e tente novamente.");
                try {
                    Thread.sleep(5000);
                } catch (Exception ev) {
                    System.err.println("Erro: " + ev);
                }
            }
        }

        respostaErrada = true;
        while (respostaErrada == true) {
            clearScreen();
            System.out.println("Deseja adiar o alarme quando tocar?");
            System.out.println("Digite a opção abaixo e tecle enter:");
            System.out.println("[s] - Sim");
            System.out.println("[n] - Não");

            respostaUsuario = inputScanner.nextLine();
            if (respostaUsuario.trim().equals("s") || respostaUsuario.trim().equals("S")) {
                adiarDespertador = true;
                respostaErrada = false;
            } else if (respostaUsuario.equals("n") || respostaUsuario.equals("N")) {
                adiarDespertador = false;
                respostaErrada = false;
            } else {
                System.out.println("Resposta inválida! Verifique sua digitação e tente novamente.");
                try {
                    Thread.sleep(5000);
                } catch (Exception ev) {
                    System.err.println("Erro: " + ev);
                }
            }
        }

        clearScreen();
        System.out.println("Digite abaixo o nome do despertador e tecle enter:");

        respostaUsuario = inputScanner.nextLine();
        nomeDespertador = respostaUsuario;

        respostaErrada = true;
        while (respostaErrada == true) {
            clearScreen();

            System.out.println("Deseja o volume crescente?");
            System.out.println("Digite abaixo a opção desejada e tecle Enter:");
            System.out.println("[s] - Sim");
            System.out.println("[n] - Não");

            respostaUsuario = inputScanner.nextLine();
            if (respostaUsuario.equals("s") || respostaUsuario.equals("S")) {
                volumeCrescente = true;
                respostaErrada = false;
            } else if (respostaUsuario.equals("n") || respostaUsuario.equals("N")) {
                volumeCrescente = false;
                respostaErrada = false;
            } else {
                System.out.println("Resposta inválida! Verifique sua digitação e tente novamente.");
                try {
                    Thread.sleep(5000);
                } catch (Exception ev) {
                    System.err.println("Erro: " + ev);
                }
            }
        }

        while (encerrarDespertador == false) {
            clearScreen();

            horaAtual = LocalDateTime.now().getHour();
            minutoAtual = LocalDateTime.now().getMinute();
            segundoAtual = LocalDateTime.now().getSecond();

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

            if (horaAtual == horaDespertar && minutoAtual >= minutoDespertar && minutoAtual <= (minutoDespertar + tempoMaximoSoneca)) {
                tocarSom(volumeCrescente);
                System.out.println("O despertador: " + nomeDespertador + " está ativo.");
                if (adiarDespertador == true) {
                    respostaErrada = true;
                    while (respostaErrada == true) {
                        System.out.println("Adiar alarme?");
                        System.out.println("Digite abaixo a opção desejada e tecle enter:");
                        System.out.println("[5] - adiar 5 minutos");
                        System.out.println("[10] - adiar 10 minutos");
                        System.out.println("[s] - sair");
                        respostaUsuario = inputScanner.nextLine().trim();
                        if (respostaUsuario.equals("s") || respostaUsuario.equals("S")) {
                            inputScanner.close();
                            System.exit(0);
                        } else if (respostaUsuario.equals("5")) {
                            minutoDespertar += 5;
                            respostaErrada = false;
                        } else if (respostaUsuario.equals("10")) {
                            minutoDespertar += 10;
                            respostaErrada = false;
                        } else {
                            System.out.println("Resposta inválida! Verifique sua digitação e tente novamente.");
                            try {
                                Thread.sleep(5000);
                            } catch (Exception ev) {
                                System.err.println("Erro: " + ev);
                            }
                        }
                    }
                    if (minutoDespertar > 59) {
                        minutoDespertar -= 59;
                        horaDespertar++;
                        if (horaDespertar > 23) {
                            horaDespertar = 0;
                        }
                    }
                }
            }

            System.out.println("Agora são: " + horaAtual + "h:" + minutoAtual + "m:" + segundoAtual + "s");

            System.out.println("O alarme irá despertar às: " + horaDespertar + "h:" + minutoDespertar + "m");

            System.out.println("Faltam: " + horaRestante + " hora(s), " + minutoRestante + " minuto(s), " + segundoRestante + " segundos para o próximo alarme.");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Erro: " + e);
            }
        }
        inputScanner.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
}