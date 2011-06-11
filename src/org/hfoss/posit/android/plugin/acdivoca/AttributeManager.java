/*
 * File: AttributeManager.java
 * 
 * Copyright (C) 2011 The Humanitarian FOSS Project (http://hfoss.org).
 * 
 * This file is part of POSIT-Haiti Server.
 *
 * POSIT-Haiti Server is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation; either version 3.0 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not visit http://www.gnu.org/licenses/gpl.html.
 *
 */

package org.hfoss.posit.android.plugin.acdivoca;

import java.util.HashMap;
import java.util.Iterator;

import org.hfoss.posit.android.R;
import org.hfoss.posit.android.api.FindPluginManager;

import android.app.Activity;
import android.util.Log;

/**
 * Manages attributes.
 *
 */
public class AttributeManager {
	public static final String TAG = "AttributeManager";
	
	private static AttributeManager mInstance = null; 
	private Activity mMainActivity = null;
	private static HashMap<String,String> abbreviations;

	
	public static final String ATTR_VAL_SEPARATOR = "=";
	public static final String PAIRS_SEPARATOR = ",";
	
	public static final String FORM_FIRST_NAME="FirstName";
	public static final String FORM_LAST_NAME="LastName";
	public static final String FORM_COMMUNE="Commune";
	public static final String FORM_SECTION="Section";
	public static final String FORM_ADDRESS="Address";
	public static final String FORM_AGE="Age";
	public static final String FORM_SEX="Sex";
	public static final String FORM_MALE="Male";
	public static final String FORM_FEMALE="Female";

	public static final String FORM_BENEFICIARY_CATEGORY="BeneficiaryCategory";
	public static final String FORM_NUMBER_IN_HOUSE= "NumberInHome"; // "Number of persons in the house:";
	public static final String FORM_HEALTH_CENTER="HealthCenter";
	public static final String FORM_DISTRIBUTION_POST="DistributionPost";
	public static final String FORM_NAME_CHILD= "NameIfChild"; // "Responsible name (if child):";
	public static final String FORM_NAME_WOMAN= "NameIfWoman";  // "Responsible name (if pregnant woman):";
	public static final String FORM_HUSBAND= "Husband"; // "Husband name (if woman):";
	public static final String FORM_FATHER= "Father";  // "Father's name (if child):";
	public static final String FORM_MOTHER_LEADER = "MotherLeader";  // "Are you a mother leader?:";
	public static final String FORM_VISIT_MOTHER = "VisitMotherLeader";   // "Do you visit a mother leader?:";
	public static final String FORM_AGRICULTURE_1 = "Agr1";  // "Is someone in your family in the";
	public static final String FORM_AGRICULTURE_2 = "Agr2";  // Agriculture Program of ACDI/VOCA?:";
	public static final String FORM_GIVE_NAME= "GiveName";  // "If yes, give the name:";
	public static final String BUTTON_YES="Yes";
	public static final String BUTTON_NO="No";
	public static final String BUTTON_MALE="MALE";
	public static final String BUTTON_FEMALE="FEMALE";
	public static final String BUTTON_INFANT_MAL="Enfant mal nourri";
	public static final String BUTTON_INFANT_PREVENTION="Enfant en prevention";
	public static final String BUTTON_MOTHER_EXPECTING="Femme enceinte";
	public static final String BUTTON_MOTHER_NURSING="Femme allaitante";
	
	
	public static final String FORM_DOSSIER="Dossier";
	public static final String FORM_DOB= "DateOfBirth";
	public static final String FORM_MONTHS= "MonthsRemaining";
	public static final String FORM_PRESENT= "Present";  // "Was the beneficiary present?";
	public static final String FORM_TRANSFERRED= "Transferred";  // "Should this beneficiary be transferred to a new category?";
	public static final String FORM_MODIFICATIONS= "Modifications";  // "Are modifications needed in the beneficiary's record?";
	public static final String FORM_SUSPEND= "Suspend";  // "Should the beneficiary be suspended?";
	public static final String FORM_WHY= "Why"; //"If so, why?:";
	
	
	public static final String ABBREV_ATTRIBUTE = "attr";
	public static final String ABBREV_FIRST = "fn";
	public static final String ABBREV_LAST = "ln";
	public static final String ABBREV_COMMUNE = "c";
	public static final String ABBREV_COMMUNE_SECTION = "cs";
	public static final String ABBREV_ADDRESS = "ad";
	public static final String ABBREV_AGE = "a";
	public static final String ABBREV_SEX = "s";
	public static final String ABBREV_BENEFICIARY = "b";
	public static final String ABBREV_NUMBER_IN_HOME = "nih";
	public static final String ABBREV_HEALTH_CENTER = "hc";
	public static final String ABBREV_DISTRIBUTION_POST = "dp";
	public static final String ABBREV_NAME_CHILD = "nc";
	public static final String ABBREV_NAME_WOMAN = "nw";
	public static final String ABBREV_HUSBAND = "h";
	public static final String ABBREV_FATHER = "f";
	public static final String ABBREV_MOTHER_LEADER = "ml";
	public static final String ABBREV_VISIT_MOTHER = "vm";
	public static final String ABBREV_AGRICULTURE_1 = "a1";
	public static final String ABBREV_AGRICULTURE_2 = "a2";
	public static final String ABBREV_GIVE_NAME = "gn";
	public static final String ABBREV_YES = "y";
	public static final String ABBREV_NO = "n";
	public static final String ABBREV_MALE = "m";
	public static final String ABBREV_FEMALE = "f";
	public static final String ABBREV_INFANT_CATEGORY = "ic";
	public static final String ABBREV_INFANT_MAL = "ia";
	public static final String ABBREV_INFANT_PREVENTION = "ip";
	public static final String ABBREV_MOTHER_CATEGORY = "mc";
	public static final String ABBREV_MOTHER_EXPECTING = "me";
	public static final String ABBREV_MOTHER_NURSING = "mn";
	public static final String ABBREV_DATA = "d";
	public static final String ABBREV_GENERAL_INFORMATION = "gi";
	public static final String ABBREV_MCHN_INFORMATION = "mchn";
	public static final String ABBREV_CONTROLS = "ctrl";
	public static final String ABBREV_STATUS = "st";
	public static final String ABBREV_ID = "id";
	public static final String ABBREV_AV = "AV";
	public static final String ABBREV_TYPE = "t";

	public static final String LONG_ATTRIBUTE = "attribute";
	public static final String LONG_FIRST = "firstName";
	public static final String LONG_LAST = "lastName";
	public static final String LONG_COMMUNE = "commune";
	public static final String LONG_COMMUNE_SECTION = "communeSection";
	public static final String LONG_ADDRESS = "address";
	public static final String LONG_AGE = "age";
	public static final String LONG_SEX = "sex";
	public static final String LONG_BENEFICIARY = "beneficiary";
	public static final String LONG_NUMBER_IN_HOME = "NumberInHome";
	public static final String LONG_HEALTH_CENTER = "HealthCenter";
	public static final String LONG_DISTRIBUTION_POST = "DistributionPost";
	public static final String LONG_NAME_CHILD = "nameChild";
	public static final String LONG_NAME_WOMAN = "nameWoman";
	public static final String LONG_HUSBAND = "husband";
	public static final String LONG_FATHER = "father";
	public static final String LONG_MOTHER_LEADER = "motherLeader";
	public static final String LONG_VISIT_MOTHER = "visitMotherLeader";
	public static final String LONG_AGRICULTURE_1 = "agriculture1";
	public static final String LONG_AGRICULTURE_2 = "agriculture2";
	public static final String LONG_GIVE_NAME = "giveName";
	public static final String LONG_YES = "yes";
	public static final String LONG_NO = "no";
	public static final String LONG_MALE = "male";
	public static final String LONG_FEMALE = "female";
	public static final String LONG_INFANT_CATEGORY = "InfantCategory";
	public static final String LONG_INFANT_MAL = "InfantMal";
	public static final String LONG_INFANT_PREVENTION = "InfantPrevention";
	public static final String LONG_MOTHER_CATEGORY = "MotherCategory";
	public static final String LONG_MOTHER_EXPECTING = "MotherExpecting";
	public static final String LONG_MOTHER_NURSING = "MotherNursing";
	public static final String LONG_DATA = "data";
	public static final String LONG_GENERAL_INFORMATION = "generalInformation";
	public static final String LONG_MCHN_INFORMATION = "mchnInformation";
	public static final String LONG_CONTROLS = "controls";
	public static final String LONG_STATUS = "status";
	public static final String LONG_ID = "id";
	public static final String LONG_AV = "AV";
	public static final String LONG_TYPE = "type";
	
	public static final String LONG_DOSSIER = "dossier";
	
	public static final String OUTER_DELIM = ",";
	public static final String INNER_DELIM = "=";
	public static final String URL_OUTER_DELIM = "%2C";
	public static final String URL_INNER_DELIM = "%3D";
	public static final String URL_PLUS = "%2B";
	public static final String PLUS = "+";
	public static final String FORM_BENEFICIARY = null;
	
	
	// Added for mobile app
	// This group refers to the abbreviated column names in the on-phone Db
	public static final String FINDS_DOSSIER =  "dossier";
	public static final String FINDS_TYPE =  "type";
	public static final String FINDS_STATUS =  "status";
	public static final String MESSAGE_TEXT =  "message";
	public static final String FINDS_MESSAGE_STATUS =  "message_status";
	public static final String FINDS_FIRSTNAME =  "firstname";
	public static final String FINDS_LASTNAME =  "lastname";
	public static final String FINDS_ADDRESS =  "address";
	public static final String FINDS_DOB =  "dob";
	public static final String FINDS_HOUSEHOLD_SIZE =  "household_size";
	public static final String FINDS_BENEFICIARY_CATEGORY =  "beneficiary_category";
	public static final String FINDS_SEX =  "sex";
	public static final String FINDS_HEALTH_CENTER =  "health_center";
	public static final String FINDS_DISTRIBUTION_POST =  "distribution_post";
	public static final String MESSAGE_BENEFICIARY_ID =  "beneficiary_id";
	public static final String MESSAGE_CREATED_AT =  "created_time";
	public static final String MESSAGE_SENT_AT =  "sent_time";
	public static final String MESSAGE_ACK_AT =  "acknowledged_time";

	/**
	 * Private constructor means it can't be instantiated.
	 * @param activity
	 */
	private AttributeManager(){
	}
	
	public static AttributeManager getInstance(){
		mInstance = new AttributeManager();
		mInstance.init();
		//assert(mInstance != null);
		return mInstance;
	}
	
	/**
	 * Default constructor, inserts all the attributes into a HashMap
	 */
//	public AttributeManager() {
	public static void init() {
		abbreviations = new HashMap<String, String>();
		abbreviations.put(ABBREV_ATTRIBUTE, LONG_ATTRIBUTE);
		abbreviations.put(ABBREV_FIRST, LONG_FIRST);
		abbreviations.put(ABBREV_LAST, LONG_LAST);
		abbreviations.put(ABBREV_COMMUNE, LONG_COMMUNE);
		abbreviations.put(ABBREV_COMMUNE_SECTION, LONG_COMMUNE_SECTION);
		abbreviations.put(ABBREV_ADDRESS, LONG_ADDRESS);
		abbreviations.put(ABBREV_AGE, LONG_AGE);
		abbreviations.put(ABBREV_SEX, LONG_SEX);
		abbreviations.put(ABBREV_BENEFICIARY, LONG_BENEFICIARY);
		abbreviations.put(ABBREV_NUMBER_IN_HOME, LONG_NUMBER_IN_HOME);
		abbreviations.put(ABBREV_HEALTH_CENTER, LONG_HEALTH_CENTER);
		abbreviations.put(ABBREV_DISTRIBUTION_POST, LONG_DISTRIBUTION_POST);
		abbreviations.put(ABBREV_NAME_CHILD, LONG_NAME_CHILD);
		abbreviations.put(ABBREV_NAME_WOMAN, LONG_NAME_WOMAN);
		abbreviations.put(ABBREV_HUSBAND, LONG_HUSBAND);
		abbreviations.put(ABBREV_FATHER, LONG_FATHER);
		abbreviations.put(ABBREV_MOTHER_LEADER, LONG_MOTHER_LEADER);
		abbreviations.put(ABBREV_VISIT_MOTHER, LONG_VISIT_MOTHER);
		abbreviations.put(ABBREV_AGRICULTURE_1, LONG_AGRICULTURE_1);
		abbreviations.put(ABBREV_AGRICULTURE_2, LONG_AGRICULTURE_2);
		abbreviations.put(ABBREV_GIVE_NAME, LONG_GIVE_NAME);
		abbreviations.put(ABBREV_YES, LONG_YES);
		abbreviations.put(ABBREV_NO, LONG_NO);
		abbreviations.put(ABBREV_MALE, LONG_MALE);
		abbreviations.put(ABBREV_FEMALE, LONG_FEMALE);
		abbreviations.put(ABBREV_INFANT_CATEGORY, LONG_INFANT_CATEGORY);
		abbreviations.put(ABBREV_INFANT_MAL, LONG_INFANT_MAL);
		abbreviations.put(ABBREV_INFANT_PREVENTION, LONG_INFANT_PREVENTION);
		abbreviations.put(ABBREV_MOTHER_CATEGORY, LONG_MOTHER_CATEGORY);
		abbreviations.put(ABBREV_MOTHER_EXPECTING, LONG_MOTHER_EXPECTING);
		abbreviations.put(ABBREV_MOTHER_NURSING, LONG_MOTHER_NURSING);
		abbreviations.put(ABBREV_DATA, LONG_DATA);
		abbreviations.put(ABBREV_GENERAL_INFORMATION, LONG_GENERAL_INFORMATION);
		abbreviations.put(ABBREV_MCHN_INFORMATION, LONG_MCHN_INFORMATION);
		abbreviations.put(ABBREV_CONTROLS, LONG_CONTROLS);
		abbreviations.put(ABBREV_STATUS,LONG_STATUS);
		abbreviations.put(ABBREV_ID,LONG_ID);
		abbreviations.put(ABBREV_AV, LONG_AV);
		abbreviations.put(ABBREV_TYPE, LONG_TYPE);
		
		
		// This group maps SMS abbreviations to full attribute names
		abbreviations.put("i", FINDS_DOSSIER);
		abbreviations.put("t", FINDS_TYPE);
		abbreviations.put( "s", FINDS_STATUS);
		abbreviations.put("t", MESSAGE_TEXT);
		abbreviations.put( "m", FINDS_MESSAGE_STATUS);
		abbreviations.put("f", FINDS_FIRSTNAME);
		abbreviations.put("l", FINDS_LASTNAME);
		abbreviations.put("a", FINDS_ADDRESS);
		abbreviations.put("b", FINDS_DOB);
		abbreviations.put("n", FINDS_HOUSEHOLD_SIZE);
		abbreviations.put("c", FINDS_BENEFICIARY_CATEGORY);
		abbreviations.put("g", FINDS_SEX);
		abbreviations.put("h", FINDS_HEALTH_CENTER);
		abbreviations.put("d", FINDS_DISTRIBUTION_POST);
		abbreviations.put("#", MESSAGE_BENEFICIARY_ID);
		abbreviations.put("t1", MESSAGE_CREATED_AT);
		abbreviations.put("t2", MESSAGE_SENT_AT);
		abbreviations.put("t3", MESSAGE_ACK_AT);
		
		
		// Added for mobile app
		// This group maps Db column names in the on-phone Db to SMS abbreviations
		abbreviations.put(FINDS_DOSSIER, "i");
		abbreviations.put(FINDS_TYPE, "t");
		abbreviations.put(FINDS_STATUS, "s");
		abbreviations.put(MESSAGE_TEXT, "t");
		abbreviations.put(FINDS_MESSAGE_STATUS, "m");
		abbreviations.put(FINDS_FIRSTNAME, "f");
		abbreviations.put(FINDS_LASTNAME, "l");
		abbreviations.put(FINDS_ADDRESS, "a");
		abbreviations.put(FINDS_DOB, "b");
		abbreviations.put(FINDS_HOUSEHOLD_SIZE, "n");
		abbreviations.put(FINDS_BENEFICIARY_CATEGORY, "c");
		abbreviations.put(FINDS_SEX, "g");
		abbreviations.put(FINDS_HEALTH_CENTER, "h");
		abbreviations.put(FINDS_DISTRIBUTION_POST, "d");
		abbreviations.put(MESSAGE_BENEFICIARY_ID, "#");
		abbreviations.put(MESSAGE_CREATED_AT, "t1");
		abbreviations.put(MESSAGE_SENT_AT, "t2");
		abbreviations.put(MESSAGE_ACK_AT, "t3");
		
		// These correspond to data values represented as Enums in the server app
		abbreviations.put("FEMALE", "F");
		abbreviations.put("MALE", "M");
		abbreviations.put("EXPECTING", "E");
		abbreviations.put("Femme Enceinte", "E");
		abbreviations.put("NURSING", "N");
		abbreviations.put("Femme Allaitante", "N");	
		abbreviations.put("PREVENTION", "P");
		abbreviations.put("Enfant Prevention", "P");		
		abbreviations.put("MALNOURISHED", "M");
		abbreviations.put("Enfant Mal", "M");	
	}
	
	/**
	 * Maps the short form field names to long form
	 * @param abbreviatedAttributes TRUE if abbreviated, FALSE if not
	 * @param s the String to be mapped to long
	 * @return the long form of the String
	 */
	public String mapToLong(Beneficiary.Abbreviated abbreviatedAttributes, String s){
		if (abbreviatedAttributes == Beneficiary.Abbreviated.TRUE) {
			String str = abbreviations.get(s);
			if (str != null)
				return str;
			else 
				return "";
		}
		else
			return s;
	}
	
	/**
	 * Maps the short form field names to long form
	 * @param abbreviatedAttributes TRUE if abbreviated, FALSE if not
	 * @param s the String to be mapped to long
	 * @return the long form of the String
	 */
	public String mapToLong(SmsMessage.Abbreviated abbreviatedAttributes, String s){
		if (abbreviatedAttributes == SmsMessage.Abbreviated.TRUE) {
			String str = abbreviations.get(s);
			if (str != null)
				return str;
			else 
				return "";
		}
		else
			return s;
	}
	
	/**
	 * Converts attribute-value pairs to abbreviated attribute-value pairs.
	 * @param attr
	 * @param val
	 * @return  a string of the form a1=v1,a2=b2, ..., aN=vN
	 */
	public static String convertAttrValPairToAbbrev(String attr, String val) {
		
		String attrAbbrev = abbreviations.get(attr);
		String valAbbrev = convertValToAbbrev(val);
		
		return attrAbbrev + ATTR_VAL_SEPARATOR + valAbbrev;
	}
	
	/**
	 * Convert a a value to an abbreviated value. This is mostly
	 * used for the names of health centers and distribution posts.
	 * @param val a String of the form "Name of Some Health Center"
	 * @return a String of the form "1" representing that health center
	 */
	public static String convertValToAbbrev(String val) {
		String abbrev = abbreviations.get(val);
		if (abbrev != null)
			return abbrev;
		else 
			return val;
	}
	
	
	public void testAllAttributes() {
		Iterator<String> it = abbreviations.values().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
	/**
	 * TODO:  This method should thoroughly test this class. For example, 
	 * print out all mappings of short to long.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello Attribue Manager");
		AttributeManager am = AttributeManager.getInstance(); // new AttributeManager();
		System.out.print(am.mapToLong(Beneficiary.Abbreviated.FALSE, "f"));
		am.testAllAttributes();
	}

}
