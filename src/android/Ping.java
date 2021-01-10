package com.spartan0nix.plugins;

import java.util.List;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.util.ArrayList;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;

import android.content.Context;


public class Ping extends CordovaPlugin {

    private String request="";
    private CallbackContext callbackContext;
    private JSONObject errorObject = new JSONObject();

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(String action, JSONArray arg, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        String ipAddress = arg.getString(0);
        if (action.equals("ipReachable")) {
                this.ipReachable(ipAddress); 
        } else if (action.equals("ping")) {
                this.ping(ipAddress);     
        } else {
          // Unsupported action
            return false;
        }
        return true;
    }

    public void ipReachable(String ipAddress) {
        try {
            InetAddress destination = InetAddress.getByName(ipAddress);
            if( destination.isReachable(5000)){
                this.request="Host is reachable";
            }else{
                this.request="Host is not reachable";
            }    
        } catch (UnknownHostException e) {
            sendError(e);
        } catch (IOException e){
            sendError(e);
        }
        // Send Result back
        PluginResult result = new PluginResult(PluginResult.Status.OK, this.request);
        callbackContext.sendPluginResult(result);
    }

    public void ping(String ipAddress) {
        try {
            this.request = "";
            String command = "/system/bin/ping -c 4 " + ipAddress;
            // Get the current runtime
            Runtime runtime = Runtime.getRuntime();
            // Create Process and execute ping command
            Process process = runtime.exec(command);
            // Read input stream of subprocess
            // new InputStreamReader => bridge from byte streams to character streams.
            // new Buffered Reader => read text from a character-input-stream
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // Read error in input-stream
            BufferedReader Error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String res;
            // Read input stream line by line until there no more line
            while((res = inputStream.readLine()) != null ) {
                this.request += "<p>" + res + "</p>";
            }
            // Read error input stream line by line until there no more line
            while((res = Error.readLine()) != null) { 
                this.request += "<p>" + res + "</p>";
            }
            //Destroy the process
            process.destroy();
        } catch(Exception e){
            sendError(e);
        }
        // Send result back
        PluginResult result = new PluginResult(PluginResult.Status.OK, this.request);
        callbackContext.sendPluginResult(result);
    }

    // Send back JSON format error message
    public void sendError(Exception err){
        try {
            this.errorObject.put("error", err);   
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PluginResult error = new PluginResult(PluginResult.Status.ERROR, this.errorObject);
        callbackContext.sendPluginResult(error);
    }
}
