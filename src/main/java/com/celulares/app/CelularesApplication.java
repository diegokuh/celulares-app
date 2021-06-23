package com.celulares.app;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.celulares.app.service.CelularesService;
import java.awt.Color;
import java.awt.Component;

@SpringBootApplication
public class CelularesApplication extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String RESULT_MESSAGE = "Los celulares recomendados son:";
	private static final String NO_RESULT_MESSAGE = "No se encontraron celulares";
	
	@Autowired
	private CelularesService celularesService;
	
	private JPanel contentPane;
	private JPanel resultPanel;
	private JComboBox cmbSo;
	private JComboBox cmbAlmacenamiento;
	private JComboBox cmbCamara;
	private JComboBox cmbColor;
	private JComboBox cmbOperador;
	
	private JLabel lblResultTitle;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(CelularesApplication.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {

        	CelularesApplication ex = ctx.getBean(CelularesApplication.class);
        	ex.populateCombos();
            ex.setVisible(true);
        });
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CelularesFrame frame = new CelularesFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}

	private void populateCombos() {
		cmbSo.setModel(new DefaultComboBoxModel(celularesService.getSosForCombo().stream().toArray(String[]::new)));
		cmbAlmacenamiento.setModel(new DefaultComboBoxModel(celularesService.getAlmacenamientosForCombo().stream().toArray(String[]::new)));
		cmbCamara.setModel(new DefaultComboBoxModel(celularesService.getCamarasForCombo().stream().toArray(String[]::new)));
		cmbColor.setModel(new DefaultComboBoxModel(celularesService.getColoresForCombo().stream().toArray(String[]::new)));
		cmbOperador.setModel(new DefaultComboBoxModel(celularesService.getOperadoresForCombo().stream().toArray(String[]::new)));
	}

	/**
	 * Create the frame.
	 */
	public CelularesApplication() {
		setTitle("Celulares App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Selecciona las caracter\u00EDsticas deseadas por categor\u00EDa");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(31, 29, 486, 25);
		contentPane.add(lblNewLabel);
		
		cmbSo = new JComboBox();
		cmbSo.setBounds(31, 91, 145, 21);
		contentPane.add(cmbSo);
		
		JLabel lblSo = new JLabel("Sistema Operativo:");
		lblSo.setBounds(31, 68, 145, 13);
		contentPane.add(lblSo);
		
		JLabel lblAlmacenamiento = new JLabel("Memoria de almacenamiento:");
		lblAlmacenamiento.setBounds(186, 64, 186, 13);
		contentPane.add(lblAlmacenamiento);
		
		cmbAlmacenamiento = new JComboBox();
		cmbAlmacenamiento.setBounds(186, 91, 145, 21);
		contentPane.add(cmbAlmacenamiento);
		
		JLabel lblCamara = new JLabel("Resoluci\u00F3n C\u00E1mara:");
		lblCamara.setBounds(31, 122, 145, 13);
		contentPane.add(lblCamara);
		
		cmbCamara = new JComboBox();
		cmbCamara.setBounds(31, 145, 145, 21);
		contentPane.add(cmbCamara);
		
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(186, 122, 145, 13);
		contentPane.add(lblColor);
		
		cmbColor = new JComboBox();
		cmbColor.setBounds(186, 145, 145, 21);
		contentPane.add(cmbColor);
		
		JLabel lblOperador = new JLabel("Operador:");
		lblOperador.setBounds(31, 176, 145, 13);
		contentPane.add(lblOperador);
		
		cmbOperador = new JComboBox();
		cmbOperador.setBounds(31, 199, 145, 21);
		contentPane.add(cmbOperador);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(categoriesSelected())goForIt();
			}
		});
		btnAceptar.setBounds(246, 268, 85, 21);
		contentPane.add(btnAceptar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbSo.setSelectedIndex(0);
				cmbAlmacenamiento.setSelectedIndex(0);
				cmbCamara.setSelectedIndex(0);
				cmbColor.setSelectedIndex(0);
				cmbOperador.setSelectedIndex(0);
			}
		});
		btnLimpiar.setBounds(31, 268, 85, 21);
		contentPane.add(btnLimpiar);
		
		resultPanel = new JPanel();
		resultPanel.setBounds(362, 85, 293, 192);
		contentPane.add(resultPanel);
		resultPanel.setLayout(null);
		
		lblResultTitle = new JLabel("");
		lblResultTitle.setName("ResultLabel");
		lblResultTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultTitle.setBounds(10, 10, 273, 13);
		lblResultTitle.setVisible(false);
		resultPanel.add(lblResultTitle);
	}
	
	private void goForIt() {
		this.removeLabels();
		List<String> celulares = celularesService.goForIt(cmbSo.getSelectedItem(), cmbAlmacenamiento.getSelectedItem(), cmbCamara.getSelectedItem(), cmbColor.getSelectedItem(), cmbOperador.getSelectedItem());
		AtomicInteger initialY = new AtomicInteger(46);
		lblResultTitle.setText(celulares.isEmpty()?NO_RESULT_MESSAGE:RESULT_MESSAGE);
		lblResultTitle.setVisible(true);
		celulares.stream().forEach(c->{
			JLabel lblCelular = new JLabel(c);
			lblCelular.setForeground(Color.BLUE);
			lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblCelular.setBounds(10, initialY.getAndAdd(20), 224, 19);
			resultPanel.add(lblCelular);
		});
		resultPanel.repaint();
	}

	private boolean categoriesSelected() {
		// TODO Auto-generated method stub
		boolean allSelected = cmbSo.getSelectedIndex()>0 && cmbAlmacenamiento.getSelectedIndex()>0 
				&& cmbCamara.getSelectedIndex()>0 && cmbColor.getSelectedIndex()>0 && cmbOperador.getSelectedIndex()>0;
		if(allSelected) return true;
		new JOptionPane().showMessageDialog(this, "Debe seleccionar un elemento en cada una de las categorías", "Información requerida", JOptionPane.WARNING_MESSAGE);
		return false;
	}
	
	private void removeLabels() {
		for(Component c : resultPanel.getComponents()) {
			if("ResultLabel".equals(c.getName()))continue;
			resultPanel.remove(c);
		}
	}
}
