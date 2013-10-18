package com.daniel.calcu8;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import java.util.*;
import android.util.Log;

public class MainActivity extends Activity implements OnClickListener {
	TextView display;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		display = (TextView) findViewById(R.id.display);
		//all the click listeners
		Button zeroButton = (Button) findViewById(R.id.zero_button);
		zeroButton.setOnClickListener(this);
		Button oneButton = (Button) findViewById(R.id.one_button);
		oneButton.setOnClickListener(this);
		Button twoButton = (Button) findViewById(R.id.two_button);
		twoButton.setOnClickListener(this);
		Button threeButton = (Button) findViewById(R.id.three_button);
		threeButton.setOnClickListener(this);
		Button fourButton = (Button) findViewById(R.id.four_button);
		fourButton.setOnClickListener(this);
		Button fiveButton = (Button) findViewById(R.id.five_button);
		fiveButton.setOnClickListener(this);
		Button sixButton = (Button) findViewById(R.id.six_button);
		sixButton.setOnClickListener(this);
		Button sevenButton = (Button) findViewById(R.id.seven_button);
		sevenButton.setOnClickListener(this);
		Button eightButton = (Button) findViewById(R.id.eight_button);
		eightButton.setOnClickListener(this);
		Button nineButton = (Button) findViewById(R.id.nine_button);
		nineButton.setOnClickListener(this);
		Button divideButton = (Button) findViewById(R.id.divide_button);
		divideButton.setOnClickListener(this);
		Button multiplyButton = (Button) findViewById(R.id.multiply_button);
		multiplyButton.setOnClickListener(this);
		Button minusButton = (Button) findViewById(R.id.minus_button);
		minusButton.setOnClickListener(this);
		Button plusButton = (Button) findViewById(R.id.plus_button);
		plusButton.setOnClickListener(this);
		Button decimalButton = (Button) findViewById(R.id.decimal_button);
		decimalButton.setOnClickListener(this);
		Button negativeButton = (Button) findViewById(R.id.negative_button);
		negativeButton.setOnClickListener(this);
		Button spaceButton = (Button) findViewById(R.id.space_button);
		spaceButton.setOnClickListener(this);
		Button evaluateButton = (Button) findViewById(R.id.evaluate_button);
		evaluateButton.setOnClickListener(this);
		Button backspaceButton = (Button) findViewById(R.id.backspace_button);
		backspaceButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
			
			case R.id.zero_button:
				display.setText(display.getText() + "0");
				break;
			case R.id.one_button:
				display.setText(display.getText() + "1");
				break;
			case R.id.two_button:
				display.setText(display.getText() + "2");
				break;
			case R.id.three_button:
				display.setText(display.getText() + "3");
				break;
			case R.id.four_button:
				display.setText(display.getText() + "4");
				break;
			case R.id.five_button:
				display.setText(display.getText() + "5");
				break;
			case R.id.six_button:
				display.setText(display.getText() + "6");
				break;
			case R.id.seven_button:
				display.setText(display.getText() + "7");
				break;
			case R.id.eight_button:
				display.setText(display.getText() + "8");
				break;
			case R.id.nine_button:
				display.setText(display.getText() + "9");
				break;
			case R.id.plus_button:
				display.setText(display.getText() + "+");
				break;
			case R.id.minus_button:
				display.setText(display.getText() + "-");
				break;
			case R.id.multiply_button:
				display.setText(display.getText() + "*");
				break;
			case R.id.divide_button:
				display.setText(display.getText() + "/");
				break;
			case R.id.decimal_button:
				display.setText(display.getText() + ".");
				break;
			case R.id.negative_button:
				display.setText(display.getText() + "-");
				break;
			case R.id.space_button:
				display.setText(display.getText() + " ");
				break;
			case R.id.backspace_button:
				if (!display.getText().equals(""))
					display.setText(display.getText().subSequence(0, display.getText().length() - 1));
				break;
			case R.id.evaluate_button:  
				evaluate();
				break;
			
				
		}
	}
	
	//evaluate the current text in display rpn style 
	public void evaluate() {
		String line = display.getText().toString();
		//get rid of spaces, tokenize input
		String[] tokens = line.split(" ");
		Stack<Double> ops = new Stack<Double>();
		for(int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			Log.w("debug", "handling token:" + token);
			if (token.length() == 0 || token.equals(" ")) {
				Log.w("debug", "handled strange token");
				continue;
			}
			if (Character.isDigit(token.charAt(0)) || (token.charAt(0) == '-' && token.length() > 1)) {	//quick way to determine if we're working with a digit or operator
				//digit
				if (token.contains(".")) {
					//decimal value
					if (Character.isDigit(token.charAt(0))) ops.push(Double.valueOf((Double.parseDouble(token))) * 1.0);
					else {
						token = token.substring(1);
						ops.push(Double.valueOf((Double.parseDouble(token) * -1)));
					}
				} 
				else {
					if (Character.isDigit(token.charAt(0))) ops.push(Integer.valueOf((Integer.parseInt(token))) * 1.0);
					else {
						token = token.substring(1);
						ops.push(Integer.valueOf((Integer.parseInt(token) * -1)) * 1.0);
					}
				}
			}
			else {
				//Log.w("debug", "handling operator");
				//operator
				if (ops.size() < 2) {
					display.setText("error");
					return;
				}
				if(token.equals("+")) {
					ops.push(ops.pop().doubleValue() + ops.pop().doubleValue());
				}

				if(token.equals("-")) {
					double subtractor = ops.pop().doubleValue();
					Log.w("debug", "doing subtraction:" + ops.peek() + " - " + subtractor + " = " + (ops.peek() - subtractor)); 
					ops.push(ops.pop().doubleValue() - subtractor);
				}

				if(token.equals("/")) {
					double denom = ops.pop().doubleValue();
					ops.push(ops.pop().doubleValue() / denom);
				}

				if(token.equals("*")) {
					ops.push(ops.pop().doubleValue() * ops.pop().doubleValue());
				}
			}
		}
		if (ops.size() != 1) {
			display.setText("error");
			Log.w("debug", ops.toString());
			return;
		}
		String output = "" + ops.pop().doubleValue();
		if (output.indexOf(".") != -1 && output.length() - output.indexOf(".") > 3) {
			output = output.substring(0,output.indexOf(".") + 3);
		}
		if (output.indexOf(".") != -1 && output.charAt(output.indexOf(".") + 1) == '0') {
			output = output.substring(0, output.indexOf("."));
		}
		display.setText(output);

	}

}
