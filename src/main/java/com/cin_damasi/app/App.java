package com.cin_damasi.app;

import com.cin_damasi.app.chinese_checker_package.*;
import com.cin_damasi.app.chinese_checker_package.BoardCreator;
import com.cin_damasi.app.chinese_checker_package.Game;
import javax.swing.*;
import javax.swing.JFrame;

import java.util.List;
/**
 * Hello world!
 *
 */
public class App
{
    static BoardCreator cb;

    public static void main( String[] args )
    {
		System.out.println(System.getProperty("java.version"));
        Runnable r = new Runnable() {
	
	        @Override
			public void run() 
			{
	            Game game = new Game();
	        }
	    };
	    SwingUtilities.invokeLater(r);
	}
}
