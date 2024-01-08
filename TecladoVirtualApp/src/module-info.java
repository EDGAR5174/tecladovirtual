import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TecladoVirtualApp extends JFrame {

    private JTextArea textoUsuario;
    private JLabel pangramaActual;
    private List<String> pangramas;
    private String pangramaActualTexto;
    private int teclasCorrectas;
    private int teclasIncorrectas;
    private List<Character> teclasDificultosas;

    public TecladoVirtualApp() {
        // Inicialización de la GUI
        setTitle("Teclado Virtual App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textoUsuario = new JTextArea();
        pangramaActual = new JLabel();

        // Crear teclado virtual
        JPanel tecladoPanel = new JPanel(new GridLayout(3, 10));
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton botonTecla = new JButton(String.valueOf(c));
            botonTecla.addActionListener(new TeclaListener());
            tecladoPanel.add(botonTecla);
        }

        // Cargar pangramas desde un archivo de texto
        cargarPangramas();

        // Inicialización de variables de seguimiento
        pangramaActualTexto = "";
        teclasCorrectas = 0;
        teclasIncorrectas = 0;
        teclasDificultosas = new ArrayList<>();

        // Agregar componentes a la GUI
        add(textoUsuario, BorderLayout.CENTER);
        add(tecladoPanel, BorderLayout.SOUTH);
        add(pangramaActual, BorderLayout.NORTH);

        // Iniciar la primera ronda
        mostrarNuevoPangrama();
        actualizarPangramaActual();

        // Configurar la ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarPangramas() {
        // Cargar pangramas desde un archivo de texto (puedes implementar esta parte)
        // Aquí, se asume una lista de pangramas de ejemplo
        pangramas = new ArrayList<>();
        pangramas.add("El veloz murciélago hindú comía feliz cardillo y kiwi.");
        pangramas.add("Jovencillo emponzoñado de whisky, ¡qué figurota exhibes!");
        // ... agregar más pangramas
    }

    private void mostrarNuevoPangrama() {
        // Seleccionar aleatoriamente un pangrama de la lista
        Collections.shuffle(pangramas);
        pangramaActualTexto = pangramas.get(0);
        pangramaActual.setText("Pangrama: " + pangramaActualTexto);
    }

    private void actualizarPangramaActual() {
        // Actualizar el pangrama que se muestra al usuario
        Random random = new Random();
        char teclaIncorrecta = pangramaActualTexto.charAt(random.nextInt(pangramaActualTexto.length()));

        if (!teclasDificultosas.contains(teclaIncorrecta)) {
            teclasDificultosas.add(teclaIncorrecta);
        }

        pangramaActual.setText("Pangrama: " + pangramaActualTexto + " (Tecla Incorrecta: " + teclaIncorrecta + ")");
    }

    private class TeclaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton botonTecla = (JButton) e.getSource();
            char teclaPresionada = botonTecla.getText().charAt(0);

            // Verificar si la tecla presionada es correcta
            if (teclaPresionada == pangramaActualTexto.charAt(textoUsuario.getText().length())) {
                teclasCorrectas++;
            } else {
                teclasIncorrectas++;
                actualizarPangramaActual();
            }

            // Actualizar el área de texto del usuario
            textoUsuario.append(String.valueOf(teclaPresionada));

            // Comprobar si se completó el pangrama actual
            if (textoUsuario.getText().equals(pangramaActualTexto)) {
                // Iniciar una nueva ronda
                mostrarNuevoPangrama();
                actualizarPangramaActual();
                textoUsuario.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TecladoVirtualApp::new);
    }
}