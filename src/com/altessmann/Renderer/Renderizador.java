/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Renderer;

/**
 *
 * @author Guilherme
 */
import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class Renderizador extends JFrame {

    /**
     * Renderizador das células
     */
    private class MeuRenderizador implements TableCellRenderer {

        // toda célula vai ser renderizada por um JLabel
        // poderia ser qualquer outro componente
        // poderia ter mais de um componente, para renderizar celulas diferentes
        private final JLabel componenteRenderizador;

        MeuRenderizador() {
            componenteRenderizador = new JLabel();
            componenteRenderizador.setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object conteudo, boolean selecionada, boolean focada, int lin, int col) {
            // atualizar componente renderizador
            componenteRenderizador.setText(String.valueOf(conteudo));
            componenteRenderizador.setHorizontalAlignment(getAlinhamento(col));
            componenteRenderizador.setBackground(getCor(lin, selecionada));
            return componenteRenderizador;
        }

        // escolhe a cor a partir da linha
        private Color getCor(int linha, boolean selecionada) {

            // linhas selecionadas são azuis
            if (selecionada) {
                return Color.CYAN;
            }

            // linhas pares são amarelas e impares são verdes
            // isso vai criar um efeito zebrado
            if (linha % 2 == 0) {
                return Color.YELLOW;
            }
            return Color.GREEN;
        }

        // escolhe o alinhamento a partir da coluna
        private int getAlinhamento(int coluna) {
            switch (coluna) {
                case 0:
                    return SwingConstants.LEFT;
                case 1:
                    return SwingConstants.CENTER;
                case 2:
                default:
                    return SwingConstants.RIGHT;
            }
        }
    }

    /**
     * Modelo pra renderizar muitas linhas
     */
    private class MeuModeloComMuitasLinhas extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return 10000;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int lin, int col) {
            return "célula (" + lin + ", " + col + ")";
        }

    }

    /**
     * Ponto de entrada do exemplo
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame janela = new Renderizador();
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setSize(640, 480);
            janela.setLocationRelativeTo(null);
            janela.setVisible(true);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Renderizador() {
        super("Exemplo");
        JTable table = new JTable(new MeuModeloComMuitasLinhas());
        table.setDefaultRenderer(Object.class, new MeuRenderizador());
        JScrollPane scroll = new JScrollPane(table);
        Container panel = getContentPane();
        panel.add(BorderLayout.CENTER, scroll);
    }
}
