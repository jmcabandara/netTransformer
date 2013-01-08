/*
 * iTransformer is an open source tool able to discover and transform
 *  IP network infrastructures.
 *  Copyright (C) 2012  http://itransformers.net
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.itransformers.topologyviewer.dialogs;

import net.itransformers.topologyviewer.gui.TopologyViewer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URISyntaxException;

public class NewProjectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField projetNameTextField;
	private JTextField baseFilePathTextField;
    private boolean isOkPressed;
    private File projectDir;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewProjectDialog dialog = new NewProjectDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewProjectDialog(final TopologyViewer frame) {
        setModal(true);
		setTitle("Create New Project");
		setBounds(100, 100, 564, 165);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("Project Name:");
			label.setBounds(10, 14, 78, 14);
			contentPanel.add(label);
		}
		{
			projetNameTextField = new JTextField();
			projetNameTextField.setColumns(10);
			projetNameTextField.setBounds(108, 11, 277, 20);
			contentPanel.add(projetNameTextField);
		}
		{
			JLabel label = new JLabel("Project Base Dir:");
			label.setBounds(10, 47, 98, 14);
			contentPanel.add(label);
		}
		{
			baseFilePathTextField = new JTextField();
			baseFilePathTextField.setColumns(10);
			baseFilePathTextField.setBounds(108, 44, 277, 20);
			contentPanel.add(baseFilePathTextField);
		}
		{
			JButton button = new JButton("Choose");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
                    File dir = new File(".");
                    if (frame.getPath() != null){
                        if (frame.getPath().getProtocol().equals("file")) {
                            try {
                                dir = new File(frame.getPath().toURI());
                            } catch (URISyntaxException e1) {
                                e1.printStackTrace();
                                JOptionPane.showMessageDialog(frame, "Invalid uri for path: " + frame.getPath(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    JFileChooser chooser = new JFileChooser(dir);
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setMultiSelectionEnabled(false);
                    int result = chooser.showOpenDialog(NewProjectDialog.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        baseFilePathTextField.setText(chooser.getSelectedFile().getAbsolutePath());
                    }

                }
			});
			button.setBounds(395, 43, 89, 23);
			contentPanel.add(button);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        onOK();
                    }
                });
                buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        projectDir = null;
                        isOkPressed = false;
                        setVisible(false);
                        dispose();
                    }
                });
                buttonPane.add(cancelButton);
			}
		}

	}

    private void onOK() {
        if (baseFilePathTextField.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this,"Can not create project. Parent directory is not specified");
            return;
        }
        File parentDir = new File(baseFilePathTextField.getText());
        if (parentDir.exists()){
            File projectDir = new File(parentDir, projetNameTextField.getText());
            if (projectDir.exists()){
                JOptionPane.showMessageDialog(this,"Can not create project. Directory already exists: "+projectDir.getAbsolutePath());
                return;
            }
            if (!projectDir.mkdir()){
                JOptionPane.showMessageDialog(this,"Can not create project. Unable to create directory: "+projectDir.getAbsolutePath());
                return;
            }
            this.projectDir = projectDir;
            isOkPressed = true;
            setVisible(false);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Can not create project. Parent directory does not exist: " + parentDir.getAbsolutePath());
            return;
        }
    }

    public File getProjectDir() {
        return projectDir;
    }
}
