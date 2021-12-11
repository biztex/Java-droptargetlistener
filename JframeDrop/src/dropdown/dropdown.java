package dropdown;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.awt.dnd.DropTarget;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import static javax.swing.JOptionPane.showMessageDialog;

public class dropdown extends JFrame implements DropTargetListener {

	int i = 0, j = 0 ,i2 = 0, z = 0;
	int cm = 0;
	String b = "D:/_my_client/コード比較用.txt";
	String[] a = new String[5];
	String[] all_txt = new String[10];
 	String list1 ="";
	String my_str_cm  = ""; 
	String my_str_cm1 = "";
	JLabel label;
	Boolean cm_flag  = false;
	Boolean ex_flag = false;
	public dropdown() {
		new DropTarget(this,this);
		setTitle("ファイルをドロップしてください");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(400, 400, 800, 400);
		label = new JLabel("activity_main.xmlをドロップしてください。");
		
		add(label);
		setVisible(true);
	}
	
	public static void main(String Args[]) {
		new dropdown();
		
	}

	 private void dumpFile(File file){
	       
	        // ファイル一覧取得
	        File[] files = file.listFiles();
	        
	        if(files == null){
	            return;
	        }

	         
	        for (File tmpFile : files) {		  		
	            
	            // ディレクトリの場合
	            if(tmpFile.isDirectory()){
	                
	                // 再帰呼び出し
//	                dumpFile(tmpFile);
	            	System.out.println("Directr");
	            	
	                
	            // ファイルの場合
	            }else{
	                System.out.println(tmpFile.getName().toString());
	                
	                try {
	        			File cfl = new File(a[0] + "\\" + tmpFile.getName());
	        		
	        			BufferedReader cbr = new BufferedReader(new FileReader(cfl));
	        			cm = 0;
	        			String cline = "";
	        			while (cline != null) {

	        				cline = cbr.readLine();
	        				if (cline == null)break;

	        				if(cline.isBlank() == true) {
	        					continue;
	        				}
	        				my_str_cm1 += cline.replaceAll("\\s","");
	        				
	        				
	        			}
	        			
	        			all_txt[cm] = my_str_cm1;
	        			cm++;
	        			System.out.println(my_str_cm1);
	        			
	        			cbr.close();
	        			
	        		}
	        		catch (IOException e) {
	        			System.out.println(e);
	        		}
	        		
	            }
	        }
	    }
	
	

	@Override
	public void drop(DropTargetDropEvent dtde) {
		// TODO Auto-generated method stub
		dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		boolean flg = false;
		String str = "<html><pre>";
		 
		 init();
		try {
			Transferable tr = dtde.getTransferable();
			
			if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				str += "文字列をドロップされました。\n";
				str += tr.getTransferData(DataFlavor.stringFlavor).toString();
				flg = true;
			}
			
			else if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				str += "ファイルがドロップされました。\n";
				List<File> list = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
				for (File file : list) {
					str += file.getPath() + "\n";
					//dropされたファイルのフルパスを取得
					a[z] = file.getPath();
					System.out.println(a[z]);
					z++;
				}
				
				flg = true;
			
			}
			str += "</pre></html>";
		}
		catch(UnsupportedFlavorException e) {
			e.printStackTrace();
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			dtde.dropComplete(flg);
				
			if (flg) 
			{
				// ドロップされたオブジェクトをJLabelに設定します。
				label.setText(str);
			} 
			else {
				// ドロップを受け取れなかった場合はこちらで。
				label.setText("ドロップしたファイルを受け取ることができませんでした。");
			}
	
		}
		
		String my_str = "";
		dumpFile(new File(a[0]));
		//ドロップされたファイルにactivity_main.xmlが含まれているか確認
		for(int AAA = 0; AAA<z; AAA++) {
			if(a[AAA].contains("activity_main.xml"))
			{		
				System.out.println("activity_main.xmlです。処理を行います。");
				
				//activity_main.xmlの内容を行ごとに文字列に格納
				
				try {
					File f = new File(a[AAA]);
					BufferedReader br = new BufferedReader(new FileReader(f));

					String line = "";
					
					while (line != null) {
						line = br.readLine();
						if (line == null) {
							System.out.println("false");
							break;
						}
						if(line.isBlank() == true) {
							continue;
						}
						my_str += line.replaceAll("\\s","");
						
				
					  	i++;
					  						
					}
					
					
					System.out.println(my_str);
					if(my_str.equals(my_str_cm)) {
						JOptionPane.showMessageDialog(null, "activity_main.xmlです。脆弱性が検出されました。");
					}
					
					else{
						JOptionPane.showMessageDialog(null, "activity_main.xmlです。脆弱性が検出されませんでした。");
					}
					
					
					br.close();
				  } catch (IOException e) {
					System.out.println(e);
				  }
			}
			
			else
			{
				JOptionPane.showMessageDialog(null, "activity_main.xmlではありません。");
				System.out.println("activity_main.xmlではありません。");
					
			}
			
			
			
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		i = 0 ; 
		z = 0;
		j = 0;
		
		my_str_cm = "";
	}
	
	 

	//比較用.txtのコードを文字列に格納
	public void init() {
		try {
			File fa = new File(b);
		
			BufferedReader br2 = new BufferedReader(new FileReader(fa));
			i2 = 0;
			String line2 = "";
			while (line2 != null) {

				line2 = br2.readLine();
				if (line2 == null)break;
//				hikaku[i2] = line2.replaceAll("\\s","");
				if(line2.isBlank() == true) {
					continue;
				}
				my_str_cm += line2.replaceAll("\\s","");
				
				
			}
			
			System.out.println(my_str_cm);
		
			br2.close();
			
		}
		catch (IOException e) {
			System.out.println(e);
		}
		
	}
	

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
		
	}



}
