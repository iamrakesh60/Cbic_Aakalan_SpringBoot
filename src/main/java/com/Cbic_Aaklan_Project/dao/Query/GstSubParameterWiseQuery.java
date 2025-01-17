package com.Cbic_Aaklan_Project.dao.Query;


import com.Cbic_Aaklan_Project.Service.DateCalculate;

public class GstSubParameterWiseQuery {
	public String QueryFor_gst1a_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa=" WITH CTE_Col6 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        cc.ZONE_CODE, \n"
				+ "        SUM(14c.DISPOSAL_OF_ARN_WITHIN_7) AS col6,\n"
				+ "        SUM(14c.NO_OF_ARN_RECEIVED_GSTN) AS col2,\n"
				+ "        SUM(14c.NO_OF_ARN_RECEIVED_CPC) AS col4\n"
				+ "    FROM \n"
				+ "        mis_gst_commcode AS cc \n"
				+ "        INNER JOIN mis_dpm_gst_14a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "        INNER JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        14c.MM_YYYY ='" + month_date + "'\n"
				+ "    GROUP BY \n"
				+ "        cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "Ordered_Col6 AS (\n"
				+ "    SELECT \n"
				+ "        ZONE_CODE, \n"
				+ "        ZONE_NAME, \n"
				+ "        col6,\n"
				+ "        col2,\n"
				+ "        col4,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY col6) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_rows\n"
				+ "    FROM CTE_Col6\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    o.ZONE_NAME,\n"
				+ "    o.ZONE_CODE,\n"
				+ "    o.col6,\n"
				+ "    o.col2,\n"
				+ "    o.col4,\n"
				+ "    CASE \n"
				+ "        WHEN total_rows % 2 = 1 THEN \n"
				+ "            (SELECT col6 \n"
				+ "             FROM Ordered_Col6 \n"
				+ "             WHERE row_num = (total_rows + 1) / 2)\n"
				+ "        ELSE \n"
				+ "            (SELECT AVG(col6) \n"
				+ "             FROM Ordered_Col6 \n"
				+ "             WHERE row_num IN (total_rows / 2, (total_rows / 2) + 1))\n"
				+ "    END AS median_1a\n"
				+ "FROM \n"
				+ "    Ordered_Col6 o\n"
				+ "ORDER BY \n"
				+ "    o.ZONE_CODE;  ";
		return queryGst14aa;
	}
	public String QueryFor_gst1a_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH CTE_Col6 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        cc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME,\n"
				+ "        SUM(14c.DISPOSAL_OF_ARN_WITHIN_7) AS col6,\n"
				+ "        SUM(14c.NO_OF_ARN_RECEIVED_GSTN) AS col2,\n"
				+ "        SUM(14c.NO_OF_ARN_RECEIVED_CPC) AS col4\n"
				+ "    FROM \n"
				+ "        mis_gst_commcode AS cc \n"
				+ "        INNER JOIN mis_dpm_gst_14a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "        INNER JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        14c.MM_YYYY = '" + month_date + "'\n"
				+ "    GROUP BY \n"
				+ "        cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME\n"
				+ "),\n"
				+ "Ordered_Col6 AS (\n"
				+ "    SELECT \n"
				+ "        ZONE_CODE, \n"
				+ "        ZONE_NAME, \n"
				+ "        COMM_NAME,\n"
				+ "        col6,\n"
				+ "        col2,\n"
				+ "        col4,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY col6) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_rows\n"
				+ "    FROM CTE_Col6\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    o.ZONE_NAME,\n"
				+ "    o.ZONE_CODE,\n"
				+ "    o.COMM_NAME,\n"
				+ "    o.col6,\n"
				+ "    o.col2,\n"
				+ "    o.col4,\n"
				+ "    CASE \n"
				+ "        WHEN total_rows % 2 = 1 THEN \n"
				+ "            (SELECT col6 \n"
				+ "             FROM Ordered_Col6 \n"
				+ "             WHERE row_num = (total_rows + 1) / 2)\n"
				+ "        ELSE \n"
				+ "            (SELECT AVG(col6) \n"
				+ "             FROM Ordered_Col6 \n"
				+ "             WHERE row_num IN (total_rows / 2, (total_rows / 2) + 1))\n"
				+ "    END AS median_1a\n"
				+ "FROM \n"
				+ "    Ordered_Col6 o\n"
				+ "WHERE \n"
				+ "    o.ZONE_CODE = '" + zone_code + "'\n"
				+ "ORDER BY \n"
				+ "    o.ZONE_CODE;\n"
				+ ";";
		return queryGst14aa;
	}
	public String QueryFor_gst1a_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH CTE_Col6 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        cc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME,\n"
				+ "        SUM(14c.DISPOSAL_OF_ARN_WITHIN_7) AS col6,\n"
				+ "        SUM(14c.NO_OF_ARN_RECEIVED_GSTN) AS col2,\n"
				+ "        SUM(14c.NO_OF_ARN_RECEIVED_CPC) AS col4\n"
				+ "    FROM \n"
				+ "        mis_gst_commcode AS cc \n"
				+ "        INNER JOIN mis_dpm_gst_14a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "        INNER JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        14c.MM_YYYY = '" + month_date + "'\n"
				+ "    GROUP BY \n"
				+ "        cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME\n"
				+ "),\n"
				+ "Ordered_Col6 AS (\n"
				+ "    SELECT \n"
				+ "        ZONE_CODE, \n"
				+ "        ZONE_NAME, \n"
				+ "        COMM_NAME,\n"
				+ "        col6,\n"
				+ "        col2,\n"
				+ "        col4,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY col6) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_rows\n"
				+ "    FROM CTE_Col6\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    o.ZONE_NAME,\n"
				+ "    o.ZONE_CODE,\n"
				+ "    o.COMM_NAME,\n"
				+ "    o.col6,\n"
				+ "    o.col2,\n"
				+ "    o.col4,\n"
				+ "    CASE \n"
				+ "        WHEN total_rows % 2 = 1 THEN \n"
				+ "            (SELECT col6 \n"
				+ "             FROM Ordered_Col6 \n"
				+ "             WHERE row_num = (total_rows + 1) / 2)\n"
				+ "        ELSE \n"
				+ "            (SELECT AVG(col6) \n"
				+ "             FROM Ordered_Col6 \n"
				+ "             WHERE row_num IN (total_rows / 2, (total_rows / 2) + 1))\n"
				+ "    END AS median_1a\n"
				+ "FROM \n"
				+ "    Ordered_Col6 o\n"
				+ "ORDER BY \n"
				+ "    o.ZONE_CODE;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst1b_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT zc.ZONE_NAME, cc.ZONE_CODE,sum(14c.DISPOSAL_OF_ARN_PV_NOT_RECEIVED) as col10,"
				+ " sum(14c.DISPOSAL_OF_ARN_PV_RECOMMENDED) as col7 "
				+ "FROM mis_gst_commcode as cc right join mis_dpm_gst_14a as 14c on cc.COMM_CODE=14c.COMM_CODE"
				+ " left join mis_gst_zonecode as zc on zc.ZONE_CODE=cc.ZONE_CODE"
				+ " where  14c.MM_YYYY='" + month_date + "'  group by ZONE_CODE;";
		return queryGst14aa;
	}
	public String QueryFor_gst1b_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT zc.ZONE_NAME, cc.ZONE_CODE,cc.COMM_NAME, " +
				"(14c.DISPOSAL_OF_ARN_PV_NOT_RECEIVED) as col10, " +
				"(14c.DISPOSAL_OF_ARN_PV_RECOMMENDED) as col7 FROM mis_gst_commcode as cc " +
				"right join mis_dpm_gst_14a as 14c on cc.COMM_CODE=14c.COMM_CODE " +
				"left join mis_gst_zonecode as zc on zc.ZONE_CODE=cc.ZONE_CODE " +
				"where  14c.MM_YYYY='" + month_date + "'  and cc.ZONE_CODE = '" + zone_code + "';";
		return queryGst14aa;
	}
	public String QueryFor_gst1b_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst1c_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n"
				+ "    COALESCE(zc.ZONE_NAME, 'N/A') AS ZONE_NAME, \n"
				+ "    COALESCE(cc.ZONE_CODE, 'N/A') AS ZONE_CODE,\n"
				+ "    COALESCE(SUM(14c.DISPOSAL_OF_ARN_DEEMED_REG), 0) AS col13,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN), 0) AS col2,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR), 0) AS col3,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC), 0) AS col4,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR), 0) AS col5,\n"
				+ "    COALESCE(\n"
				+ "        (COALESCE(SUM(14c.DISPOSAL_OF_ARN_DEEMED_REG), 0) / \n"
				+ "        NULLIF(\n"
				+ "            COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN), 0) \n"
				+ "            + COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR), 0) \n"
				+ "            + COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC), 0) \n"
				+ "            + COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR), 0), \n"
				+ "        0)\n"
				+ "        ) * 100, \n"
				+ "    0) AS total_score1c\n"
				+ "FROM \n"
				+ "    mis_gst_commcode AS cc\n"
				+ "RIGHT JOIN \n"
				+ "    mis_dpm_gst_14a AS 14c \n"
				+ "    ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "LEFT JOIN \n"
				+ "    mis_gst_zonecode AS zc \n"
				+ "    ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "WHERE \n"
				+ "    MM_YYYY = '" + month_date + "'-- Replace with the dynamic value as needed\n"
				+ "GROUP BY \n"
				+ "    cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "ORDER BY \n"
				+ "    total_score1c;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst1c_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n"
				+ "    COALESCE(zc.ZONE_NAME, 'N/A') AS ZONE_NAME, \n"
				+ "    COALESCE(cc.ZONE_CODE, 'N/A') AS ZONE_CODE,\n"
				+ "    COALESCE(cc.COMM_NAME, 'N/A') AS COMM_NAME, -- Added COMM_NAME\n"
				+ "    COALESCE(SUM(14c.DISPOSAL_OF_ARN_DEEMED_REG), 0) AS col13,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN), 0) AS col2,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR), 0) AS col3,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC), 0) AS col4,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR), 0) AS col5,\n"
				+ "    COALESCE(\n"
				+ "        (COALESCE(SUM(14c.DISPOSAL_OF_ARN_DEEMED_REG), 0) / \n"
				+ "        NULLIF(\n"
				+ "            COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN), 0) \n"
				+ "            + COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR), 0) \n"
				+ "            + COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC), 0) \n"
				+ "            + COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR), 0), \n"
				+ "        0)\n"
				+ "        ) * 100, \n"
				+ "    0) AS total_score1c\n"
				+ "FROM \n"
				+ "    mis_gst_commcode AS cc\n"
				+ "RIGHT JOIN \n"
				+ "    mis_dpm_gst_14a AS 14c \n"
				+ "    ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "LEFT JOIN \n"
				+ "    mis_gst_zonecode AS zc \n"
				+ "    ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "WHERE \n"
				+ "    MM_YYYY = '" + month_date + "' -- Replace with the dynamic value as needed\n"
				+ "    AND cc.ZONE_CODE = '" + zone_code + "' -- Added filter for zone_code\n"
				+ "GROUP BY \n"
				+ "    cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Added cc.COMM_NAME to GROUP BY\n"
				+ "ORDER BY \n"
				+ "    total_score1c;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst1c_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n"
				+ "    COALESCE(zc.ZONE_NAME, 'N/A') AS ZONE_NAME, \n"
				+ "    COALESCE(cc.ZONE_CODE, 'N/A') AS ZONE_CODE,\n"
				+ "    COALESCE(cc.COMM_NAME, 'N/A') AS COMM_NAME, -- Added COMM_NAME\n"
				+ "    COALESCE(SUM(14c.DISPOSAL_OF_ARN_DEEMED_REG), 0) AS col13,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN), 0) AS col2,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR), 0) AS col3,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC), 0) AS col4,\n"
				+ "    COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR), 0) AS col5,\n"
				+ "    COALESCE(\n"
				+ "        (COALESCE(SUM(14c.DISPOSAL_OF_ARN_DEEMED_REG), 0) / \n"
				+ "        NULLIF(\n"
				+ "            COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN), 0) \n"
				+ "            + COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR), 0) \n"
				+ "            + COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC), 0) \n"
				+ "            + COALESCE(SUM(14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR), 0), \n"
				+ "        0)\n"
				+ "        ) * 100, \n"
				+ "    0) AS total_score1c\n"
				+ "FROM \n"
				+ "    mis_gst_commcode AS cc\n"
				+ "RIGHT JOIN \n"
				+ "    mis_dpm_gst_14a AS 14c \n"
				+ "    ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "LEFT JOIN \n"
				+ "    mis_gst_zonecode AS zc \n"
				+ "    ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "WHERE \n"
				+ "    MM_YYYY = '" + month_date + "' -- Replace with the dynamic value as needed\n"
				+ "GROUP BY \n"
				+ "    cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Added cc.COMM_NAME to GROUP BY\n"
				+ "ORDER BY \n"
				+ "    total_score1c;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst1d_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT\n"
				+ "    IFNULL(zc.ZONE_NAME, 'N/A') AS ZONE_NAME,\n"
				+ "    IFNULL(cc.ZONE_CODE, 'N/A') AS ZONE_CODE,\n"
				+ "    IFNULL(SUM((t14c.OPENING_BALANCE + t14c.NO_OF_ARN_RECEIVED_GSTN + t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR +\n"
				+ "                t14c.NO_OF_ARN_RECEIVED_CPC + t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR) -\n"
				+ "               (t14c.DISPOSAL_OF_ARN_TRANSFERED_CPC + t14c.DISPOSAL_OF_ARN_DEEMED_REG +\n"
				+ "                t14c.DISPOSAL_OF_ARN_WITHIN_7 + t14c.DISPOSAL_OF_ARN_PV_APPROVED + t14c.DISPOSAL_OF_ARN_PV_REJECTED)), 0) AS col17,\n"
				+ "    IFNULL(SUM(t14c.OPENING_BALANCE), 0) AS col1,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_GSTN), 0) AS col2,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR), 0) AS col3,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_CPC), 0) AS col4,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR), 0) AS col5,\n"
				+ "    -- Adding total_score column with rounding\n"
				+ "    IFNULL(ROUND(\n"
				+ "        (SUM((t14c.OPENING_BALANCE + t14c.NO_OF_ARN_RECEIVED_GSTN + t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR +\n"
				+ "              t14c.NO_OF_ARN_RECEIVED_CPC + t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR) -\n"
				+ "            (t14c.DISPOSAL_OF_ARN_TRANSFERED_CPC + t14c.DISPOSAL_OF_ARN_DEEMED_REG +\n"
				+ "             t14c.DISPOSAL_OF_ARN_WITHIN_7 + t14c.DISPOSAL_OF_ARN_PV_APPROVED + t14c.DISPOSAL_OF_ARN_PV_REJECTED))) /\n"
				+ "        (SUM(t14c.OPENING_BALANCE) + SUM(t14c.NO_OF_ARN_RECEIVED_GSTN) +\n"
				+ "         SUM(t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR) + SUM(t14c.NO_OF_ARN_RECEIVED_CPC) +\n"
				+ "         SUM(t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR)) * 100, 2), 0) AS total_score\n"
				+ "FROM\n"
				+ "    mis_gst_commcode cc\n"
				+ "RIGHT JOIN\n"
				+ "    mis_dpm_gst_14a t14c ON cc.COMM_CODE = t14c.COMM_CODE\n"
				+ "LEFT JOIN\n"
				+ "    mis_gst_zonecode zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "WHERE\n"
				+ "    MM_YYYY = '" + month_date + "'\n"
				+ "GROUP BY\n"
				+ "    cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "ORDER BY\n"
				+ "    total_score ASC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst1d_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT\n"
				+ "    IFNULL(zc.ZONE_NAME, 'N/A') AS ZONE_NAME,\n"
				+ "    IFNULL(cc.ZONE_CODE, 'N/A') AS ZONE_CODE,\n"
				+ "    IFNULL(cc.COMM_NAME, 'N/A') AS COMM_NAME,  -- Added COMM_NAME\n"
				+ "    IFNULL(SUM((t14c.OPENING_BALANCE + t14c.NO_OF_ARN_RECEIVED_GSTN + t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR +\n"
				+ "                t14c.NO_OF_ARN_RECEIVED_CPC + t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR) -\n"
				+ "               (t14c.DISPOSAL_OF_ARN_TRANSFERED_CPC + t14c.DISPOSAL_OF_ARN_DEEMED_REG +\n"
				+ "                t14c.DISPOSAL_OF_ARN_WITHIN_7 + t14c.DISPOSAL_OF_ARN_PV_APPROVED + t14c.DISPOSAL_OF_ARN_PV_REJECTED)), 0) AS col17,\n"
				+ "    IFNULL(SUM(t14c.OPENING_BALANCE), 0) AS col1,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_GSTN), 0) AS col2,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR), 0) AS col3,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_CPC), 0) AS col4,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR), 0) AS col5,\n"
				+ "    -- Adding total_score column with rounding\n"
				+ "    IFNULL(ROUND(\n"
				+ "        (SUM((t14c.OPENING_BALANCE + t14c.NO_OF_ARN_RECEIVED_GSTN + t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR +\n"
				+ "              t14c.NO_OF_ARN_RECEIVED_CPC + t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR) -\n"
				+ "            (t14c.DISPOSAL_OF_ARN_TRANSFERED_CPC + t14c.DISPOSAL_OF_ARN_DEEMED_REG +\n"
				+ "             t14c.DISPOSAL_OF_ARN_WITHIN_7 + t14c.DISPOSAL_OF_ARN_PV_APPROVED + t14c.DISPOSAL_OF_ARN_PV_REJECTED))) /\n"
				+ "        (SUM(t14c.OPENING_BALANCE) + SUM(t14c.NO_OF_ARN_RECEIVED_GSTN) +\n"
				+ "         SUM(t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR) + SUM(t14c.NO_OF_ARN_RECEIVED_CPC) +\n"
				+ "         SUM(t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR)) * 100, 2), 0) AS total_score\n"
				+ "FROM\n"
				+ "    mis_gst_commcode cc\n"
				+ "RIGHT JOIN\n"
				+ "    mis_dpm_gst_14a t14c ON cc.COMM_CODE = t14c.COMM_CODE\n"
				+ "LEFT JOIN\n"
				+ "    mis_gst_zonecode zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "WHERE\n"
				+ "    MM_YYYY = '" + month_date + "'\n"
				+ "    AND cc.ZONE_CODE = '" + zone_code + "' -- Added the condition for ZONE_CODE\n"
				+ "GROUP BY\n"
				+ "    cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME  -- Added COMM_NAME to GROUP BY\n"
				+ "ORDER BY\n"
				+ "    total_score ASC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst1d_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT\n"
				+ "    IFNULL(zc.ZONE_NAME, 'N/A') AS ZONE_NAME,\n"
				+ "    IFNULL(cc.ZONE_CODE, 'N/A') AS ZONE_CODE,\n"
				+ "    IFNULL(cc.COMM_NAME, 'N/A') AS COMM_NAME,  -- Added COMM_NAME\n"
				+ "    IFNULL(SUM((t14c.OPENING_BALANCE + t14c.NO_OF_ARN_RECEIVED_GSTN + t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR +\n"
				+ "                t14c.NO_OF_ARN_RECEIVED_CPC + t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR) -\n"
				+ "               (t14c.DISPOSAL_OF_ARN_TRANSFERED_CPC + t14c.DISPOSAL_OF_ARN_DEEMED_REG +\n"
				+ "                t14c.DISPOSAL_OF_ARN_WITHIN_7 + t14c.DISPOSAL_OF_ARN_PV_APPROVED + t14c.DISPOSAL_OF_ARN_PV_REJECTED)), 0) AS col17,\n"
				+ "    IFNULL(SUM(t14c.OPENING_BALANCE), 0) AS col1,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_GSTN), 0) AS col2,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR), 0) AS col3,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_CPC), 0) AS col4,\n"
				+ "    IFNULL(SUM(t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR), 0) AS col5,\n"
				+ "    -- Adding total_score column with rounding\n"
				+ "    IFNULL(ROUND(\n"
				+ "        (SUM((t14c.OPENING_BALANCE + t14c.NO_OF_ARN_RECEIVED_GSTN + t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR +\n"
				+ "              t14c.NO_OF_ARN_RECEIVED_CPC + t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR) -\n"
				+ "            (t14c.DISPOSAL_OF_ARN_TRANSFERED_CPC + t14c.DISPOSAL_OF_ARN_DEEMED_REG +\n"
				+ "             t14c.DISPOSAL_OF_ARN_WITHIN_7 + t14c.DISPOSAL_OF_ARN_PV_APPROVED + t14c.DISPOSAL_OF_ARN_PV_REJECTED))) /\n"
				+ "        (SUM(t14c.OPENING_BALANCE) + SUM(t14c.NO_OF_ARN_RECEIVED_GSTN) +\n"
				+ "         SUM(t14c.NO_OF_ARN_RECEIVED_GSTN_WITHOUT_AADHAR) + SUM(t14c.NO_OF_ARN_RECEIVED_CPC) +\n"
				+ "         SUM(t14c.NO_OF_ARN_RECEIVED_CPC_WITHOUT_AADHAR)) * 100, 2), 0) AS total_score\n"
				+ "FROM\n"
				+ "    mis_gst_commcode cc\n"
				+ "RIGHT JOIN\n"
				+ "    mis_dpm_gst_14a t14c ON cc.COMM_CODE = t14c.COMM_CODE\n"
				+ "LEFT JOIN\n"
				+ "    mis_gst_zonecode zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "WHERE\n"
				+ "    MM_YYYY = '" + month_date + "'\n"
				+ "GROUP BY\n"
				+ "    cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME  -- Added COMM_NAME to GROUP BY\n"
				+ "ORDER BY\n"
				+ "    total_score ASC;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst1e_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE,\n" +
				"    SUM(\n" +
				"        (14c.SUO_MOTO_OPENING_BALANCE + 14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION - 14c.SUO_MOTO_GSTIN_CANCELLED) +\n" +
				"        (14c.CANCELLATION_OPENING_BALANCE + 14c.CANCELLATION_NO_APPLICATION_RECEIVED - 14c.CANCELLATION_GSTIN_CANCELLED)\n" +
				"    ) AS col9,\n" +
				"    SUM(14c.SUO_MOTO_OPENING_BALANCE) AS col1,\n" +
				"    SUM(14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION) AS col2,\n" +
				"    SUM(14c.CANCELLATION_OPENING_BALANCE) AS col5,\n" +
				"    SUM(14c.CANCELLATION_NO_APPLICATION_RECEIVED) AS col6,\n" +
				"    (SUM(\n" +
				"        (14c.SUO_MOTO_OPENING_BALANCE + 14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION - 14c.SUO_MOTO_GSTIN_CANCELLED) +\n" +
				"        (14c.CANCELLATION_OPENING_BALANCE + 14c.CANCELLATION_NO_APPLICATION_RECEIVED - 14c.CANCELLATION_GSTIN_CANCELLED)\n" +
				"    ) / \n" +
				"    (SUM(14c.SUO_MOTO_OPENING_BALANCE) + \n" +
				"    SUM(14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION) + \n" +
				"    SUM(14c.CANCELLATION_OPENING_BALANCE) + \n" +
				"    SUM(14c.CANCELLATION_NO_APPLICATION_RECEIVED))) * 100 AS total_score\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN \n" +
				"    mis_dpm_gst_15a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE \n" +
				"    14c.MM_YYYY = '" + month_date + "'\n" +
				"GROUP BY \n" +
				"    cc.ZONE_CODE, zc.ZONE_NAME\n" +
				"ORDER BY \n" +
				"    total_score ASC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst1e_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE,\n" +
				"    cc.COMM_NAME,\n" +
				"    ((tbl14c.SUO_MOTO_OPENING_BALANCE + \n" +
				"      tbl14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION - \n" +
				"      tbl14c.SUO_MOTO_GSTIN_CANCELLED) +\n" +
				"     (tbl14c.CANCELLATION_OPENING_BALANCE + \n" +
				"      tbl14c.CANCELLATION_NO_APPLICATION_RECEIVED - \n" +
				"      tbl14c.CANCELLATION_GSTIN_CANCELLED)) AS col9,\n" +
				"    tbl14c.SUO_MOTO_OPENING_BALANCE AS col1,\n" +
				"    tbl14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION AS col2,\n" +
				"    tbl14c.CANCELLATION_OPENING_BALANCE AS col5,\n" +
				"    tbl14c.CANCELLATION_NO_APPLICATION_RECEIVED AS col6,\n" +
				"    ((tbl14c.SUO_MOTO_OPENING_BALANCE + \n" +
				"      tbl14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION - \n" +
				"      tbl14c.SUO_MOTO_GSTIN_CANCELLED) +\n" +
				"     (tbl14c.CANCELLATION_OPENING_BALANCE + \n" +
				"      tbl14c.CANCELLATION_NO_APPLICATION_RECEIVED - \n" +
				"      tbl14c.CANCELLATION_GSTIN_CANCELLED)) / \n" +
				"    (tbl14c.SUO_MOTO_OPENING_BALANCE +\n" +
				"     tbl14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION +\n" +
				"     tbl14c.CANCELLATION_OPENING_BALANCE +\n" +
				"     tbl14c.CANCELLATION_NO_APPLICATION_RECEIVED) * 100 AS total_score\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN \n" +
				"    mis_dpm_gst_15a AS tbl14c ON cc.COMM_CODE = tbl14c.COMM_CODE\n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE \n" +
				"    MM_YYYY = '" + month_date + "' \n" +
				"    AND cc.ZONE_CODE = '" + zone_code + "'\n" +
				"ORDER BY \n" +
				"    total_score ASC\n" +
				"LIMIT 0, 1000;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst1e_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE,\n" +
				"    cc.COMM_NAME,\n" +
				"    ((tbl14c.SUO_MOTO_OPENING_BALANCE +\n" +
				"      tbl14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION -\n" +
				"      tbl14c.SUO_MOTO_GSTIN_CANCELLED) +\n" +
				"     (tbl14c.CANCELLATION_OPENING_BALANCE +\n" +
				"      tbl14c.CANCELLATION_NO_APPLICATION_RECEIVED -\n" +
				"      tbl14c.CANCELLATION_GSTIN_CANCELLED)) AS col9,\n" +
				"    tbl14c.SUO_MOTO_OPENING_BALANCE AS col1,\n" +
				"    tbl14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION AS col2,\n" +
				"    tbl14c.CANCELLATION_OPENING_BALANCE AS col5,\n" +
				"    tbl14c.CANCELLATION_NO_APPLICATION_RECEIVED AS col6,\n" +
				"    (((\n" +
				"        tbl14c.SUO_MOTO_OPENING_BALANCE +\n" +
				"        tbl14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION -\n" +
				"        tbl14c.SUO_MOTO_GSTIN_CANCELLED) +\n" +
				"       (tbl14c.CANCELLATION_OPENING_BALANCE +\n" +
				"        tbl14c.CANCELLATION_NO_APPLICATION_RECEIVED -\n" +
				"        tbl14c.CANCELLATION_GSTIN_CANCELLED))\n" +
				"        / (tbl14c.SUO_MOTO_OPENING_BALANCE +\n" +
				"           tbl14c.SUO_MOTO_GSTIN_LIABLE_FOR_CANCELLATION +\n" +
				"           tbl14c.CANCELLATION_OPENING_BALANCE +\n" +
				"           tbl14c.CANCELLATION_NO_APPLICATION_RECEIVED)) * 100 AS total_score\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN \n" +
				"    mis_dpm_gst_15a AS tbl14c ON cc.COMM_CODE = tbl14c.COMM_CODE\n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE \n" +
				"    tbl14c.MM_YYYY ='" + month_date + "'\n" +
				"ORDER BY \n" +
				"    total_score ASC;\n";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst1f_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE,\n" +
				"    SUM(15a.REVOCATION_OPENING_BALANCE + 15a.REVOCATION_ARN_RECEIVED - 15a.REVOCATION_GSTIN_REVOKED - 15a.REVOCATION_APPLICATION_REJECTED) AS col14,\n" +
				"    SUM(15a.REVOCATION_OPENING_BALANCE) AS col10,\n" +
				"    SUM(15a.REVOCATION_ARN_RECEIVED) AS col11,\n" +
				"    (SUM(15a.REVOCATION_OPENING_BALANCE + 15a.REVOCATION_ARN_RECEIVED - 15a.REVOCATION_GSTIN_REVOKED - 15a.REVOCATION_APPLICATION_REJECTED) / \n" +
				"    (SUM(15a.REVOCATION_OPENING_BALANCE) + SUM(15a.REVOCATION_ARN_RECEIVED)) * 100) AS total_score\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN \n" +
				"    mis_dpm_gst_15a AS 15a ON cc.COMM_CODE = 15a.COMM_CODE\n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE \n" +
				"    MM_YYYY = '" + month_date + "'\n" +
				"GROUP BY \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE\n" +
				"ORDER BY \n" +
				"    total_score ASC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst1f_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE,\n" +
				"    cc.COMM_NAME,\n" +
				"    (15a.REVOCATION_OPENING_BALANCE + 15a.REVOCATION_ARN_RECEIVED - 15a.REVOCATION_GSTIN_REVOKED - 15a.REVOCATION_APPLICATION_REJECTED) AS col14,\n" +
				"    15a.REVOCATION_OPENING_BALANCE AS col10,\n" +
				"    15a.REVOCATION_ARN_RECEIVED AS col11,\n" +
				"    ((15a.REVOCATION_OPENING_BALANCE + 15a.REVOCATION_ARN_RECEIVED - 15a.REVOCATION_GSTIN_REVOKED - 15a.REVOCATION_APPLICATION_REJECTED) / \n" +
				"     (15a.REVOCATION_OPENING_BALANCE + 15a.REVOCATION_ARN_RECEIVED)) * 100 AS total_score\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN \n" +
				"    mis_dpm_gst_15a AS 15a ON cc.COMM_CODE = 15a.COMM_CODE\n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE \n" +
				"    MM_YYYY = '" + month_date + "'\n" +
				"    AND cc.ZONE_CODE = '" + zone_code + "'\n" +
				"ORDER BY \n" +
				"    total_score ASC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst1f_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE,\n" +
				"    cc.COMM_NAME,\n" +
				"    (15a.REVOCATION_OPENING_BALANCE + 15a.REVOCATION_ARN_RECEIVED - 15a.REVOCATION_GSTIN_REVOKED - 15a.REVOCATION_APPLICATION_REJECTED) AS col14,\n" +
				"    15a.REVOCATION_OPENING_BALANCE AS col10,\n" +
				"    15a.REVOCATION_ARN_RECEIVED AS col11,\n" +
				"    (15a.REVOCATION_OPENING_BALANCE + 15a.REVOCATION_ARN_RECEIVED - 15a.REVOCATION_GSTIN_REVOKED - 15a.REVOCATION_APPLICATION_REJECTED) / \n" +
				"    (15a.REVOCATION_OPENING_BALANCE + 15a.REVOCATION_ARN_RECEIVED) * 100 AS total_score\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN \n" +
				"    mis_dpm_gst_15a AS 15a ON cc.COMM_CODE = 15a.COMM_CODE\n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE \n" +
				"    MM_YYYY = '" + month_date + "'\n" +
				"ORDER BY \n" +
				"    total_score ASC;\n";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst2_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT zc.ZONE_NAME, cc.ZONE_CODE, SUM(14c.GSTR_3BM_F - 14c.GSTR_3BM_D) AS col21, SUM(14c.GSTR_3BM_F) AS col3,\n"
				+ "    CASE WHEN SUM(14c.GSTR_3BM_F) <> 0 \n"
				+ "         THEN (SUM(14c.GSTR_3BM_F - 14c.GSTR_3BM_D) / SUM(14c.GSTR_3BM_F)) * 100\n"
				+ "         ELSE 0 END AS total_score,\n"
				+ "    DENSE_RANK() OVER (ORDER BY \n"
				+ "        CASE WHEN SUM(14c.GSTR_3BM_F) <> 0 \n"
				+ "             THEN (SUM(14c.GSTR_3BM_F - 14c.GSTR_3BM_D) / SUM(14c.GSTR_3BM_F)) * 100\n"
				+ "             ELSE 0 END ) AS z_rank\n"
				+ "FROM mis_gst_commcode AS cc \n"
				+ "RIGHT JOIN mis_gst_gst_2 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n"
				+ "LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE \n"
				+ "WHERE 14c.MM_YYYY = '"+ month_date+"' \n"
				+ "GROUP BY zc.ZONE_NAME,cc.ZONE_CODE\n"
				+ "ORDER BY total_score ;";
		return queryGst14aa;
	}
	public String QueryFor_gst2_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa=  "SELECT zc.ZONE_NAME, cc.COMM_NAME, cc.ZONE_CODE,(14c.GSTR_3BM_F-14c.GSTR_3BM_D) AS col21,14c.GSTR_3BM_F as col3 "
				+ "FROM  mis_gst_commcode as cc right join mis_gst_gst_2 as 14c on cc.COMM_CODE=14c.COMM_CODE "
				+ "left join mis_gst_zonecode as zc on zc.ZONE_CODE=cc.ZONE_CODE "
				+ "where  14c.MM_YYYY='"+ month_date+"' and zc.ZONE_CODE='"+zone_code+"';";
		return queryGst14aa;
	}
	public String QueryFor_gst2_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT zc.ZONE_NAME, cc.COMM_NAME, cc.ZONE_CODE,(14c.GSTR_3BM_F-14c.GSTR_3BM_D) AS col21,14c.GSTR_3BM_F as col3 \n" +
				"FROM  mis_gst_commcode as cc right join mis_gst_gst_2 as 14c on cc.COMM_CODE=14c.COMM_CODE \n" +
				"left join mis_gst_zonecode as zc on zc.ZONE_CODE=cc.ZONE_CODE \n" +
				"where  14c.MM_YYYY='"+ month_date+"';";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst3a_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);

		String queryGst14aa= "WITH CTE AS (\n"
				+ "    SELECT\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        -- Calculate col1 for the previous month\n"
				+ "        SUM(CASE\n"
				+ "                WHEN 14c.MM_YYYY = DATE_FORMAT(DATE_SUB('"+ month_date+"', INTERVAL 1 MONTH), '%Y-%m-%d')\n"
				+ "                THEN COALESCE(14c.CLOSING_NO, 0)\n"
				+ "                ELSE 0\n"
				+ "            END) AS col1,\n"
				+ "        -- Other columns for the specified month only\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.RETURN_SCRUTINY, 0) ELSE 0 END) AS col2,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.SCRUTINIZED_DISCRIPANCY_FOUND, 0) ELSE 0 END) AS col4,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.OUTCOME_ASMT_12_ISSUED, 0) ELSE 0 END) AS col8,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.OUTCOME_SECTION_61_ACTION_65_66, 0) ELSE 0 END) AS col9,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.OUTCOME_SECTION_61_ACTION_67, 0) ELSE 0 END) AS col10,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.OUTCOME_SCN_SECTION_73_74, 0) ELSE 0 END) AS col11,\n"
				+ "        -- Calculate col3a as (col4 + col8 + col9 + col10 + col11)\n"
				+ "        SUM(CASE\n"
				+ "                WHEN 14c.MM_YYYY = '"+ month_date+"' THEN\n"
				+ "                    COALESCE(14c.SCRUTINIZED_DISCRIPANCY_FOUND, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_ASMT_12_ISSUED, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_SECTION_61_ACTION_65_66, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_SECTION_61_ACTION_67, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_SCN_SECTION_73_74, 0)\n"
				+ "                ELSE 0\n"
				+ "            END) AS col3a\n"
				+ "    FROM\n"
				+ "        mis_gst_zonecode AS zc\n"
				+ "        LEFT JOIN mis_gst_commcode AS cc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "        LEFT JOIN mis_dggst_gst_scr_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    WHERE\n"
				+ "        14c.MM_YYYY IN ('"+ month_date+"', DATE_FORMAT(DATE_SUB('"+ month_date+"', INTERVAL 1 MONTH), '%Y-%m-%d'))\n"
				+ "    GROUP BY\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.ZONE_CODE\n"
				+ "),\n"
				+ "MedianCalc AS (\n"
				+ "    SELECT \n"
				+ "        col3a, \n"
				+ "        ROW_NUMBER() OVER (ORDER BY col3a) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_rows\n"
				+ "    FROM CTE\n"
				+ "),\n"
				+ "MedianResult AS (\n"
				+ "    SELECT \n"
				+ "        AVG(col3a) AS median_3a\n"
				+ "    FROM MedianCalc\n"
				+ "    WHERE row_num IN (FLOOR((total_rows + 1) / 2), CEIL((total_rows + 1) / 2))\n"
				+ ")\n"
				+ "SELECT\n"
				+ "    ZONE_NAME,\n"
				+ "    ZONE_CODE,\n"
				+ "    col1,\n"
				+ "    col2,\n"
				+ "    col4,\n"
				+ "    col8,\n"
				+ "    col9,\n"
				+ "    col10,\n"
				+ "    col11,\n"
				+ "    col3a,\n"
				+ "    CONCAT(col3a, '/', (col1 + col2)) AS absval, -- Add absval column in p/q form\n"
				+ "    (SELECT median_3a FROM MedianResult) AS median_3a,\n"
				+ "    ((col3a / (col1 + col2)) * 100) AS total_score -- Added total_score column\n"
				+ "FROM CTE\n"
				+ "ORDER BY\n"
				+ "    total_score DESC, -- Keep total_score in descending order\n"
				+ "    ZONE_NAME,\n"
				+ "    ZONE_CODE;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst3a_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH CTE AS (\n"
				+ "    SELECT\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        -- Calculate col1 for the previous month\n"
				+ "        SUM(CASE\n"
				+ "                WHEN 14c.MM_YYYY = DATE_FORMAT(DATE_SUB('"+ month_date+"', INTERVAL 1 MONTH), '%Y-%m-%d')\n"
				+ "                THEN COALESCE(14c.CLOSING_NO, 0)\n"
				+ "                ELSE 0\n"
				+ "            END) AS col1,\n"
				+ "        -- Other columns for the specified month only\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.RETURN_SCRUTINY, 0) ELSE 0 END) AS col2,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.SCRUTINIZED_DISCRIPANCY_FOUND, 0) ELSE 0 END) AS col4,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.OUTCOME_ASMT_12_ISSUED, 0) ELSE 0 END) AS col8,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.OUTCOME_SECTION_61_ACTION_65_66, 0) ELSE 0 END) AS col9,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.OUTCOME_SECTION_61_ACTION_67, 0) ELSE 0 END) AS col10,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '"+ month_date+"' THEN COALESCE(14c.OUTCOME_SCN_SECTION_73_74, 0) ELSE 0 END) AS col11,\n"
				+ "        -- Calculate col3a as (col4 + col8 + col9 + col10 + col11)\n"
				+ "        SUM(CASE\n"
				+ "                WHEN 14c.MM_YYYY = '"+ month_date+"' THEN\n"
				+ "                    COALESCE(14c.SCRUTINIZED_DISCRIPANCY_FOUND, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_ASMT_12_ISSUED, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_SECTION_61_ACTION_65_66, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_SECTION_61_ACTION_67, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_SCN_SECTION_73_74, 0)\n"
				+ "                ELSE 0\n"
				+ "            END) AS col3a\n"
				+ "    FROM\n"
				+ "        mis_gst_zonecode AS zc\n"
				+ "        LEFT JOIN mis_gst_commcode AS cc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "        LEFT JOIN mis_dggst_gst_scr_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    WHERE\n"
				+ "        14c.MM_YYYY IN ('"+ month_date+"', DATE_FORMAT(DATE_SUB('"+ month_date+"', INTERVAL 1 MONTH), '%Y-%m-%d'))\n"
				+ "    GROUP BY\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME -- Added COMM_NAME to GROUP BY\n"
				+ "),\n"
				+ "MedianCalc AS (\n"
				+ "    SELECT \n"
				+ "        col3a, \n"
				+ "        ROW_NUMBER() OVER (ORDER BY col3a) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_rows\n"
				+ "    FROM CTE\n"
				+ "),\n"
				+ "MedianResult AS (\n"
				+ "    SELECT \n"
				+ "        AVG(col3a) AS median_3a\n"
				+ "    FROM MedianCalc\n"
				+ "    WHERE row_num IN (FLOOR((total_rows + 1) / 2), CEIL((total_rows + 1) / 2))\n"
				+ ")\n"
				+ "SELECT\n"
				+ "    COALESCE(ZONE_NAME, 'N/A') AS ZONE_NAME,\n"
				+ "    COALESCE(ZONE_CODE, 'N/A') AS ZONE_CODE,\n"
				+ "    COALESCE(COMM_NAME, 'N/A') AS COMM_NAME, -- Replace NULL with 'N/A'\n"
				+ "    COALESCE(col1, 0) AS col1,\n"
				+ "    COALESCE(col2, 0) AS col2,\n"
				+ "    COALESCE(col4, 0) AS col4,\n"
				+ "    COALESCE(col8, 0) AS col8,\n"
				+ "    COALESCE(col9, 0) AS col9,\n"
				+ "    COALESCE(col10, 0) AS col10,\n"
				+ "    COALESCE(col11, 0) AS col11,\n"
				+ "    COALESCE(col3a, 0) AS col3a,\n"
				+ "    CONCAT(COALESCE(col3a, 0), '/', (COALESCE(col1, 0) + COALESCE(col2, 0))) AS absval, -- Add absval column in p/q form\n"
				+ "    COALESCE((SELECT median_3a FROM MedianResult), 0) AS median_3a,\n"
				+ "    COALESCE(((col3a / NULLIF((col1 + col2), 0)) * 100), 0) AS total_score -- Added total_score column\n"
				+ "FROM CTE\n"
				+ "WHERE ZONE_CODE = '" + zone_code + "' -- Added filter for ZONE_CODE\n"
				+ "ORDER BY\n"
				+ "    total_score DESC, -- Keep total_score in descending order\n"
				+ "    ZONE_NAME,\n"
				+ "    ZONE_CODE;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst3a_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH CTE AS (\n"
				+ "    SELECT\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        -- Calculate col1 for the previous month\n"
				+ "        SUM(CASE\n"
				+ "                WHEN 14c.MM_YYYY = DATE_FORMAT(DATE_SUB('" + month_date + "', INTERVAL 1 MONTH), '%Y-%m-%d')\n"
				+ "                THEN COALESCE(14c.CLOSING_NO, 0)\n"
				+ "                ELSE 0\n"
				+ "            END) AS col1,\n"
				+ "        -- Other columns for the specified month only\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '" + month_date + "' THEN COALESCE(14c.RETURN_SCRUTINY, 0) ELSE 0 END) AS col2,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '" + month_date + "' THEN COALESCE(14c.SCRUTINIZED_DISCRIPANCY_FOUND, 0) ELSE 0 END) AS col4,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '" + month_date + "' THEN COALESCE(14c.OUTCOME_ASMT_12_ISSUED, 0) ELSE 0 END) AS col8,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '" + month_date + "'THEN COALESCE(14c.OUTCOME_SECTION_61_ACTION_65_66, 0) ELSE 0 END) AS col9,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '" + month_date + "'THEN COALESCE(14c.OUTCOME_SECTION_61_ACTION_67, 0) ELSE 0 END) AS col10,\n"
				+ "        SUM(CASE WHEN 14c.MM_YYYY = '" + month_date + "'THEN COALESCE(14c.OUTCOME_SCN_SECTION_73_74, 0) ELSE 0 END) AS col11,\n"
				+ "        -- Calculate col3a as (col4 + col8 + col9 + col10 + col11)\n"
				+ "        SUM(CASE\n"
				+ "                WHEN 14c.MM_YYYY = '" + month_date + "' THEN\n"
				+ "                    COALESCE(14c.SCRUTINIZED_DISCRIPANCY_FOUND, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_ASMT_12_ISSUED, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_SECTION_61_ACTION_65_66, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_SECTION_61_ACTION_67, 0)\n"
				+ "                    + COALESCE(14c.OUTCOME_SCN_SECTION_73_74, 0)\n"
				+ "                ELSE 0\n"
				+ "            END) AS col3a\n"
				+ "    FROM\n"
				+ "        mis_gst_zonecode AS zc\n"
				+ "        LEFT JOIN mis_gst_commcode AS cc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "        LEFT JOIN mis_dggst_gst_scr_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    WHERE\n"
				+ "        14c.MM_YYYY IN ('" + month_date + "', DATE_FORMAT(DATE_SUB('" + month_date + "', INTERVAL 1 MONTH), '%Y-%m-%d'))\n"
				+ "    GROUP BY\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME -- Added COMM_NAME to GROUP BY\n"
				+ "),\n"
				+ "MedianCalc AS (\n"
				+ "    SELECT \n"
				+ "        col3a, \n"
				+ "        ROW_NUMBER() OVER (ORDER BY col3a) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_rows\n"
				+ "    FROM CTE\n"
				+ "),\n"
				+ "MedianResult AS (\n"
				+ "    SELECT \n"
				+ "        AVG(col3a) AS median_3a\n"
				+ "    FROM MedianCalc\n"
				+ "    WHERE row_num IN (FLOOR((total_rows + 1) / 2), CEIL((total_rows + 1) / 2))\n"
				+ ")\n"
				+ "SELECT\n"
				+ "    COALESCE(ZONE_NAME, 'N/A') AS ZONE_NAME,\n"
				+ "    COALESCE(ZONE_CODE, 'N/A') AS ZONE_CODE,\n"
				+ "    COALESCE(COMM_NAME, 'N/A') AS COMM_NAME, -- Replace NULL with 'N/A'\n"
				+ "    COALESCE(col1, 0) AS col1,\n"
				+ "    COALESCE(col2, 0) AS col2,\n"
				+ "    COALESCE(col4, 0) AS col4,\n"
				+ "    COALESCE(col8, 0) AS col8,\n"
				+ "    COALESCE(col9, 0) AS col9,\n"
				+ "    COALESCE(col10, 0) AS col10,\n"
				+ "    COALESCE(col11, 0) AS col11,\n"
				+ "    COALESCE(col3a, 0) AS col3a,\n"
				+ "    CONCAT(COALESCE(col3a, 0), '/', (COALESCE(col1, 0) + COALESCE(col2, 0))) AS absval, -- Add absval column in p/q form\n"
				+ "    COALESCE((SELECT median_3a FROM MedianResult), 0) AS median_3a,\n"
				+ "    COALESCE(((col3a / NULLIF((col1 + col2), 0)) * 100), 0) AS total_score -- Added total_score column\n"
				+ "FROM CTE\n"
				+ "ORDER BY\n"
				+ "    total_score DESC, -- Keep total_score in descending order\n"
				+ "    ZONE_NAME,\n"
				+ "    ZONE_CODE;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst3b_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa="WITH ranked_data AS (\n"
				+ "    SELECT \n"
				+ "        COALESCE(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), 0) AS col26,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY COALESCE(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), 0)) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_count\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_dggst_gst_scr_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    WHERE 14c.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "'\n"
				+ "    GROUP BY cc.ZONE_CODE\n"
				+ "),\n"
				+ "median_data AS (\n"
				+ "    SELECT \n"
				+ "        CASE \n"
				+ "            WHEN total_count % 2 = 1 THEN \n"
				+ "                (SELECT col26 FROM ranked_data WHERE row_num = (total_count + 1) / 2)\n"
				+ "            ELSE \n"
				+ "                (SELECT COALESCE(AVG(col26), 0) FROM ranked_data WHERE row_num IN ((total_count / 2), (total_count / 2) + 1))\n"
				+ "        END AS median_numerator_3b\n"
				+ "    FROM ranked_data\n"
				+ "    LIMIT 1\n"
				+ ")\n"
				+ "SELECT t.ZONE_NAME, t.ZONE_CODE, t.score_of_parameter, t.absval, t.col26, t.col17, \n"
				+ "    m.median_numerator_3b\n"
				+ "FROM (\n"
				+ "    SELECT zc.ZONE_NAME, cc.ZONE_CODE, \n"
				+ "        COALESCE(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), 0) AS col26,\n"
				+ "        COALESCE(SUM(14c.TAX_LIABILITY_DETECTECT), 0) AS col17,\n"
				+ "        COALESCE((SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY) * 100) / SUM(14c.TAX_LIABILITY_DETECTECT), 0) AS score_of_parameter,\n"
				+ "        CONCAT(\n"
				+ "            COALESCE(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), 0), \n"
				+ "            ' / ', \n"
				+ "            COALESCE(SUM(14c.TAX_LIABILITY_DETECTECT), 0)\n"
				+ "        ) AS absval\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_dggst_gst_scr_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 14c.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "'\n"
				+ "    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ ") AS t\n"
				+ "CROSS JOIN median_data m\n"
				+ "ORDER BY t.score_of_parameter DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst3b_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa="WITH ranked_data AS (\n"
				+ "    SELECT \n"
				+ "        SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY) AS col26,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY)) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_count\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_dggst_gst_scr_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    WHERE 14c.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "'\n"
				+ "    GROUP BY cc.ZONE_CODE\n"
				+ "),\n"
				+ "final_data AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME,  \n"
				+ "        SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY) AS col26,\n"
				+ "        SUM(14c.TAX_LIABILITY_DETECTECT) AS col17,\n"
				+ "        (SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY) * 100) / SUM(14c.TAX_LIABILITY_DETECTECT) AS score_of_parameter,\n"
				+ "        CONCAT(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), ' / ', SUM(14c.TAX_LIABILITY_DETECTECT)) AS absval\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_dggst_gst_scr_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 14c.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "'\n"
				+ "    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME\n"
				+ "),\n"
				+ "ranked_final_data AS (\n"
				+ "    SELECT \n"
				+ "        t.ZONE_NAME,\n"
				+ "        t.ZONE_CODE,\n"
				+ "        t.COMM_NAME,  \n"
				+ "        t.score_of_parameter,\n"
				+ "        t.absval,\n"
				+ "        t.col26,\n"
				+ "        t.col17,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY t.col26) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_count\n"
				+ "    FROM final_data t\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    t.ZONE_NAME,\n"
				+ "    t.ZONE_CODE,\n"
				+ "    t.COMM_NAME, \n"
				+ "    t.score_of_parameter,\n"
				+ "    t.absval,\n"
				+ "    t.col26,\n"
				+ "    t.col17,\n"
				+ "    -- Median Calculation\n"
				+ "    CASE\n"
				+ "        WHEN t.total_count % 2 = 1 THEN\n"
				+ "            -- Odd total count, take the middle row\n"
				+ "            (SELECT t2.col26 FROM ranked_final_data t2 WHERE t2.row_num = (t.total_count + 1) / 2)\n"
				+ "        ELSE\n"
				+ "            -- Even total count, take the average of the two middle rows\n"
				+ "            (SELECT AVG(t2.col26) \n"
				+ "             FROM ranked_final_data t2 \n"
				+ "             WHERE t2.row_num IN (t.total_count / 2, (t.total_count / 2) + 1))\n"
				+ "    END AS median_col26\n"
				+ "FROM ranked_final_data t\n"
				+ "WHERE t.ZONE_CODE = '" + zone_code + "'\n"
				+ "ORDER BY t.score_of_parameter DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst3b_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa=  "WITH ranked_data AS (\n"
				+ "    SELECT \n"
				+ "        COALESCE(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), 0) AS col26,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY COALESCE(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), 0)) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_count\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_dggst_gst_scr_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    WHERE 14c.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "'\n"
				+ "    GROUP BY cc.ZONE_CODE\n"
				+ "),\n"
				+ "final_data AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME,\n"
				+ "        COALESCE(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), 0) AS col26,\n"
				+ "        COALESCE(SUM(14c.TAX_LIABILITY_DETECTECT), 0) AS col17,\n"
				+ "        (COALESCE(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), 0) * 100) / \n"
				+ "        NULLIF(COALESCE(SUM(14c.TAX_LIABILITY_DETECTECT), 0), 0) AS score_of_parameter,\n"
				+ "        CONCAT(\n"
				+ "            COALESCE(SUM(14c.AMOUNT_RECOVERED_TAX + 14c.AMOUNT_RECOVERED_INTEREST + 14c.AMOUNT_RECOVERED_PENALTY), 0), \n"
				+ "            ' / ', \n"
				+ "            COALESCE(SUM(14c.TAX_LIABILITY_DETECTECT), 0)\n"
				+ "        ) AS absval\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_dggst_gst_scr_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 14c.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "'\n"
				+ "    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME\n"
				+ "),\n"
				+ "ranked_final_data AS (\n"
				+ "    SELECT \n"
				+ "        t.ZONE_NAME,\n"
				+ "        t.ZONE_CODE,\n"
				+ "        t.COMM_NAME,\n"
				+ "        t.score_of_parameter,\n"
				+ "        t.absval,\n"
				+ "        t.col26,\n"
				+ "        t.col17,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY t.col26) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_count\n"
				+ "    FROM final_data t\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    t.ZONE_NAME,\n"
				+ "    t.ZONE_CODE,\n"
				+ "    t.COMM_NAME, \n"
				+ "    COALESCE(t.score_of_parameter, 0) AS score_of_parameter,\n"
				+ "    COALESCE(t.absval, '0 / 0') AS absval,\n"
				+ "    COALESCE(t.col26, 0) AS col26,\n"
				+ "    COALESCE(t.col17, 0) AS col17,\n"
				+ "    -- Median Calculation\n"
				+ "    CASE\n"
				+ "        WHEN t.total_count % 2 = 1 THEN\n"
				+ "            -- Odd total count, take the middle row\n"
				+ "            COALESCE((SELECT t2.col26 FROM ranked_final_data t2 WHERE t2.row_num = (t.total_count + 1) / 2), 0)\n"
				+ "        ELSE\n"
				+ "            -- Even total count, take the average of the two middle rows\n"
				+ "            COALESCE((SELECT AVG(t2.col26) \n"
				+ "                      FROM ranked_final_data t2 \n"
				+ "                      WHERE t2.row_num IN (t.total_count / 2, (t.total_count / 2) + 1)), 0)\n"
				+ "    END AS median_col26\n"
				+ "FROM ranked_final_data t\n"
				+ "ORDER BY t.score_of_parameter DESC;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst4a_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="-- ----gst4a zone wise \n" +
				"WITH CTE AS (\n" +
				"SELECT zc.ZONE_NAME,cc.ZONE_CODE,\n" +
				"SUM(14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) AS col13, SUM(14c.OPENING_BALANCE_NO) AS col1,\n" +
				"((SUM(14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) * 100) / SUM(14c.OPENING_BALANCE_NO)) AS total_score\n" +
				"FROM mis_gst_commcode cc RIGHT JOIN mis_gi_gst_2 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 14c.MM_YYYY = '" + month_date + "' GROUP BY cc.ZONE_CODE\n" +
				"),\n" +
				"CTE_ranked AS (\n" +
				"SELECT CTE.*, \n" +
				"ROW_NUMBER() OVER (ORDER BY col13 ASC) AS row_num,\n" +
				"COUNT(*) OVER () AS total_rows\n" +
				"FROM CTE\n" +
				")\n" +
				"SELECT CTE_ranked.*,\n" +
				"(SELECT AVG(subquery.col13)\n" +
				"FROM ( SELECT col13\n" +
				"FROM CTE_ranked WHERE row_num IN (FLOOR((total_rows + 1) / 2), CEIL((total_rows + 1) / 2))\n" +
				") AS subquery) AS median_4a\n" +
				"FROM CTE_ranked ORDER BY total_score DESC;";
//        String queryGst14aa= "SELECT zc.ZONE_NAME,  cc.ZONE_CODE, SUM(14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) AS col13,  SUM(14c.OPENING_BALANCE_NO) AS col1 ,\n" +
//                "((SUM(14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) * 100)/ SUM(14c.OPENING_BALANCE_NO)) as total_score\n" +
//                "FROM mis_gst_commcode AS cc\n" +
//                "RIGHT JOIN mis_gi_gst_2 AS 14c  ON cc.COMM_CODE = 14c.COMM_CODE \n" +
//                "LEFT JOIN    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
//                "WHERE 14c.MM_YYYY = '"+ month_date+"' GROUP BY  cc.ZONE_CODE\n" +
//                "order by total_score desc;";
		return queryGst14aa;
	}
	public String QueryFor_gst4a_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH RankedData AS (\n" +
				"SELECT zc.ZONE_NAME, cc.ZONE_CODE,cc.COMM_NAME, (14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) AS col13,\n" +
				"(14c.OPENING_BALANCE_NO) AS col1,\n" +
				"((14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) * 100 / 14c.OPENING_BALANCE_NO) AS score_of_parameter4a,\n" +
				"ROW_NUMBER() OVER (ORDER BY (14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO)) AS row_num,\n" +
				"COUNT(*) OVER () AS total_count\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_gi_gst_2 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 14c.MM_YYYY = '" + month_date + "'),\n" +
				"MedianData AS (\n" +
				"SELECT ZONE_NAME, ZONE_CODE, COMM_NAME, col13, col1, score_of_parameter4a,\n" +
				"(SELECT AVG(col13) FROM (SELECT col13\n" +
				"FROM RankedData WHERE\n" +
				"row_num IN (FLOOR((total_count + 1) / 2.0), CEIL((total_count + 1) / 2.0))\n" +
				") AS MedianSubquery\n" +
				") AS median_4a\n" +
				"FROM RankedData\n" +
				")\n" +
				"SELECT ZONE_NAME, ZONE_CODE, COMM_NAME, col13, col1, score_of_parameter4a, median_4a\n" +
				"FROM MedianData WHERE ZONE_CODE = '" + zone_code + "'\n" +
				"ORDER BY score_of_parameter4a DESC;";
//        String queryGst14aa="SELECT zc.ZONE_NAME,cc.ZONE_CODE,cc.COMM_NAME,\n" +
//                "                            (14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) AS col13,\n" +
//                "                            (14c.OPENING_BALANCE_NO) AS col1,\n" +
//                "                            ((14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) * 100 / (14c.OPENING_BALANCE_NO)) as score_of_parameter4a\n" +
//                "                        FROM mis_gst_commcode AS cc\n" +
//                "                        right  join  mis_gi_gst_2 as 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
//                "                        LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
//                "                        WHERE 14c.MM_YYYY = '" + month_date+ "' and cc.ZONE_CODE = '" + zone_code + "'\n" +
//                "                        order by score_of_parameter4a desc;";
		return queryGst14aa;
	}
	public String QueryFor_gst4a_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH RankedData AS (\n" +
				"SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME, (14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) AS col13, (14c.OPENING_BALANCE_NO) AS col1,\n" +
				"((14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) * 100 / 14c.OPENING_BALANCE_NO) AS score_of_parameter4a,\n" +
				"ROW_NUMBER() OVER (ORDER BY (14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO)) AS row_num, COUNT(*) OVER () AS total_count\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_gi_gst_2 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 14c.MM_YYYY = '" + month_date + "'),\n" +
				"MedianData AS (\n" +
				"SELECT ZONE_NAME, ZONE_CODE, COMM_NAME, col13,col1,score_of_parameter4a,\n" +
				"(SELECT AVG(col13)\n" +
				"FROM\n" +
				"(SELECT col13 FROM RankedData\n" +
				"WHERE row_num IN (FLOOR((total_count + 1) / 2.0), CEIL((total_count + 1) / 2.0))\n" +
				") AS MedianSubquery\n" +
				") AS median_4a\n" +
				"FROM RankedData )\n" +
				"SELECT ZONE_NAME,ZONE_CODE,COMM_NAME,col13,col1,score_of_parameter4a,median_4a\n" +
				"FROM MedianData ORDER BY score_of_parameter4a DESC;";
//        String queryGst14aa="SELECT zc.ZONE_NAME,cc.ZONE_CODE,cc.COMM_NAME,\n" +
//                "                            (14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) AS col13,\n" +
//                "                            (14c.OPENING_BALANCE_NO) AS col1,\n" +
//                "                            ((14c.SCN_NO + 14c.VOLUNTARY_NO + 14c.MERIT_NO + 14c.TRANSFER_NO) * 100 / (14c.OPENING_BALANCE_NO)) as score_of_parameter4a\n" +
//                "                        FROM mis_gst_commcode AS cc\n" +
//                "                        right  join  mis_gi_gst_2 as 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
//                "                        LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
//                "                        WHERE 14c.MM_YYYY = '" + month_date+ "'\n" +
//                "                        order by score_of_parameter4a desc;";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst4b_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa=" SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE,\n" +
				"    SUM(2c.BETWEEN_1_TO_2_NO) AS col29,\n" +
				"    SUM(2c.MORE_THAN_2_NO) AS col31,\n" +
				"    SUM(2c.OPENING_BALANCE_NO + 2c.RECEIPT_NO - \n" +
				"        (2c.SCN_NO + 2c.VOLUNTARY_NO + 2c.MERIT_NO + 2c.TRANSFER_NO)) AS col25,\n" +
				"    (SUM(2c.BETWEEN_1_TO_2_NO) + SUM(2c.MORE_THAN_2_NO)) / \n" +
				"    NULLIF(SUM(2c.OPENING_BALANCE_NO + 2c.RECEIPT_NO - \n" +
				"        (2c.SCN_NO + 2c.VOLUNTARY_NO + 2c.MERIT_NO + 2c.TRANSFER_NO)), 0) * 100 AS total_score4b\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN \n" +
				"    mis_gi_gst_2 AS 2c ON cc.COMM_CODE = 2c.COMM_CODE\n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE \n" +
				"    2c.MM_YYYY = '"+month_date+"'\n" +
				"GROUP BY \n" +
				"    cc.ZONE_CODE, zc.ZONE_NAME\n" +
				"ORDER BY \n" +
				"    total_score4b ASC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst4b_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE, \n" +
				"    cc.COMM_NAME, \n" +
				"    SUM(2c.BETWEEN_1_TO_2_NO) AS col29, \n" +
				"    SUM(2c.MORE_THAN_2_NO) AS col31, \n" +
				"    SUM(2c.OPENING_BALANCE_NO + 2c.RECEIPT_NO - \n" +
				"        (2c.SCN_NO + 2c.VOLUNTARY_NO + 2c.MERIT_NO + 2c.TRANSFER_NO)) AS col25, \n" +
				"    ((SUM(2c.BETWEEN_1_TO_2_NO) + SUM(2c.MORE_THAN_2_NO)) / \n" +
				"        SUM(2c.OPENING_BALANCE_NO + 2c.RECEIPT_NO - \n" +
				"            (2c.SCN_NO + 2c.VOLUNTARY_NO + 2c.MERIT_NO + 2c.TRANSFER_NO)) \n" +
				"    ) * 100 AS total_score4b\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc \n" +
				"RIGHT JOIN \n" +
				"    mis_gi_gst_2 AS 2c ON cc.COMM_CODE = 2c.COMM_CODE \n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE \n" +
				"WHERE \n" +
				"    2c.MM_YYYY = '" + month_date + "' \n" +
				"    AND cc.ZONE_CODE = '" + zone_code + "'\n" +
				"GROUP BY \n" +
				"    cc.ZONE_CODE, \n" +
				"    cc.COMM_NAME, \n" +
				"    zc.ZONE_NAME\n" +
				"ORDER BY \n" +
				"    total_score4b ASC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst4b_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa=   "SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE, \n" +
				"    cc.COMM_NAME, \n" +
				"    SUM(2c.BETWEEN_1_TO_2_NO) AS col29, \n" +
				"    SUM(2c.MORE_THAN_2_NO) AS col31, \n" +
				"    SUM(2c.OPENING_BALANCE_NO + 2c.RECEIPT_NO - \n" +
				"        (2c.SCN_NO + 2c.VOLUNTARY_NO + 2c.MERIT_NO + 2c.TRANSFER_NO)) AS col25, \n" +
				"    ROUND(((SUM(2c.BETWEEN_1_TO_2_NO) + SUM(2c.MORE_THAN_2_NO)) / \n" +
				"           SUM(2c.OPENING_BALANCE_NO + 2c.RECEIPT_NO - \n" +
				"               (2c.SCN_NO + 2c.VOLUNTARY_NO + 2c.MERIT_NO + 2c.TRANSFER_NO))) * 100, 2) AS total_score4b \n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc \n" +
				"RIGHT JOIN \n" +
				"    mis_gi_gst_2 AS 2c ON cc.COMM_CODE = 2c.COMM_CODE \n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE \n" +
				"WHERE \n" +
				"    2c.MM_YYYY = '" + month_date + "' \n" +
				"GROUP BY \n" +
				"    cc.ZONE_CODE, \n" +
				"    cc.COMM_NAME \n" +
				"ORDER BY \n" +
				"    total_score4b ASC;\n";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst4c_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH FirstQuery AS (\n"
				+ "    SELECT zc.ZONE_NAME, cc.ZONE_CODE,\n"
				+ "           SUM(14c.DETECTION_CGST_AMT + 14c.DETECTION_SGST_AMT + 14c.DETECTION_IGST_AMT + 14c.DETECTION_CESS_AMT) AS col1_7\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_gi_gst_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 14c.MM_YYYY = '" + month_date + "'\n"
				+ "    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "SecondQuery AS (\n"
				+ "    SELECT zc.ZONE_NAME, cc.ZONE_CODE,\n"
				+ "           SUM(7c.GROSS_TAX_CGST_FOR_C + 7c.GROSS_TAX_SGST_FOR_C + 7c.GROSS_TAX_IGST_FOR_C + 7c.GROSS_TAX_CESS_FOR_C) * 100 AS col1_8 -- convert crore into lakhs\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_ddm_gst_1 AS 7c ON cc.COMM_CODE = 7c.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 7c.MM_YYYY = '" + month_date + "'\n"
				+ "    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "CombinedQuery AS (\n"
				+ "    SELECT \n"
				+ "        COALESCE(fq.ZONE_NAME, sq.ZONE_NAME) AS ZONE_NAME,\n"
				+ "        COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) AS ZONE_CODE,\n"
				+ "        fq.col1_7, sq.col1_8,\n"
				+ "        CONCAT(fq.col1_7, '/', sq.col1_8) AS avsvl,\n"
				+ "        (fq.col1_7 * 100 / sq.col1_8) AS score_of_parameter4c\n"
				+ "    FROM FirstQuery fq\n"
				+ "    LEFT JOIN SecondQuery sq ON fq.ZONE_CODE = sq.ZONE_CODE\n"
				+ "    WHERE COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) REGEXP '^[0-9]+$'\n"
				+ "    \n"
				+ "    UNION ALL\n"
				+ "    \n"
				+ "    SELECT \n"
				+ "        COALESCE(fq.ZONE_NAME, sq.ZONE_NAME) AS ZONE_NAME,\n"
				+ "        COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) AS ZONE_CODE,\n"
				+ "        fq.col1_7, sq.col1_8,\n"
				+ "        CONCAT(fq.col1_7, '/', sq.col1_8) AS avsvl,\n"
				+ "        (fq.col1_7 * 100 / sq.col1_8) AS score_of_parameter4c\n"
				+ "    FROM SecondQuery sq\n"
				+ "    LEFT JOIN FirstQuery fq ON fq.ZONE_CODE = sq.ZONE_CODE\n"
				+ "    WHERE fq.ZONE_CODE IS NULL\n"
				+ "    AND COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) REGEXP '^[0-9]+$'\n"
				+ "),\n"
				+ "RankedQuery AS (\n"
				+ "    SELECT *,\n"
				+ "           ROW_NUMBER() OVER (ORDER BY col1_7) AS rn,\n"
				+ "           COUNT(*) OVER () AS total_count\n"
				+ "    FROM CombinedQuery\n"
				+ "),\n"
				+ "MedianCalculation AS (\n"
				+ "    SELECT *,\n"
				+ "           CASE\n"
				+ "               WHEN total_count % 2 = 1 THEN \n"
				+ "                   (SELECT col1_7 FROM RankedQuery WHERE rn = (total_count + 1) / 2)\n"
				+ "               ELSE \n"
				+ "                   (SELECT AVG(col1_7) FROM RankedQuery WHERE rn IN (total_count / 2, total_count / 2 + 1))\n"
				+ "           END AS median_4c\n"
				+ "    FROM RankedQuery\n"
				+ ")\n"
				+ "SELECT ZONE_NAME, ZONE_CODE, col1_7, col1_8, avsvl, score_of_parameter4c, median_4c\n"
				+ "FROM MedianCalculation\n"
				+ "ORDER BY score_of_parameter4c DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst4c_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH FirstQuery AS (\n" +
				"    SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME,\n" +
				"           14c.DETECTION_CGST_AMT + 14c.DETECTION_SGST_AMT + 14c.DETECTION_IGST_AMT + 14c.DETECTION_CESS_AMT AS col1_7\n" +
				"    FROM mis_gst_commcode AS cc\n" +
				"    RIGHT JOIN mis_gi_gst_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE 14c.MM_YYYY = '" + month_date + "' \n" +
				"), \n" +
				"SecondQuery AS (\n" +
				"    SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME,\n" +
				"           (7c.GROSS_TAX_CGST_FOR_C + 7c.GROSS_TAX_SGST_FOR_C + 7c.GROSS_TAX_IGST_FOR_C + 7c.GROSS_TAX_CESS_FOR_C) * 100 AS col1_8\n" +
				"    FROM mis_gst_commcode AS cc\n" +
				"    RIGHT JOIN mis_ddm_gst_1 AS 7c ON cc.COMM_CODE = 7c.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE 7c.MM_YYYY = '" + month_date + "' \n" +
				"),\n" +
				"CombinedQuery AS (\n" +
				"    SELECT \n" +
				"        COALESCE(fq.ZONE_NAME, sq.ZONE_NAME) AS ZONE_NAME,\n" +
				"        COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) AS ZONE_CODE,\n" +
				"        COALESCE(fq.COMM_NAME, sq.COMM_NAME) AS COMM_NAME,\n" +
				"        fq.col1_7, sq.col1_8,\n" +
				"        CONCAT((fq.col1_7), '/', (sq.col1_8)) AS avsvl,\n" +
				"        (fq.col1_7 * 100 / sq.col1_8) AS score_of_subparameter4c\n" +
				"    FROM FirstQuery fq \n" +
				"    LEFT JOIN SecondQuery sq ON fq.ZONE_CODE = sq.ZONE_CODE AND fq.COMM_NAME = sq.COMM_NAME\n" +
				"    WHERE COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) REGEXP '^[0-9]+$'\n" +
				"\n" +
				"    UNION ALL\n" +
				"\n" +
				"    SELECT \n" +
				"        COALESCE(fq.ZONE_NAME, sq.ZONE_NAME) AS ZONE_NAME,\n" +
				"        COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) AS ZONE_CODE,\n" +
				"        COALESCE(fq.COMM_NAME, sq.COMM_NAME) AS COMM_NAME,\n" +
				"        fq.col1_7, sq.col1_8,\n" +
				"        CONCAT((fq.col1_7), '/', (sq.col1_8)) AS avsvl,\n" +
				"        (fq.col1_7 * 100 / sq.col1_8) AS score_of_subparameter4c\n" +
				"    FROM SecondQuery sq \n" +
				"    LEFT JOIN FirstQuery fq ON fq.ZONE_CODE = sq.ZONE_CODE AND fq.COMM_NAME = sq.COMM_NAME\n" +
				"    WHERE fq.ZONE_CODE IS NULL \n" +
				"    AND COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) REGEXP '^[0-9]+$'\n" +
				"),\n" +
				"RankedQuery AS (\n" +
				"    SELECT *,\n" +
				"           ROW_NUMBER() OVER (ORDER BY col1_7 ASC) AS row_num,\n" +
				"           COUNT(*) OVER () AS total_rows\n" +
				"    FROM CombinedQuery\n" +
				"),\n" +
				"MedianValue AS (\n" +
				"    SELECT \n" +
				"        CASE \n" +
				"            WHEN total_rows % 2 = 1 THEN \n" +
				"                (SELECT col1_7 FROM RankedQuery WHERE row_num = (total_rows + 1) / 2)\n" +
				"            ELSE \n" +
				"                (SELECT AVG(col1_7) FROM RankedQuery WHERE row_num IN (total_rows / 2, total_rows / 2 + 1))\n" +
				"        END AS median_col1_7\n" +
				"    FROM RankedQuery\n" +
				"    LIMIT 1 -- To ensure that the median is returned as a single value\n" +
				")\n" +
				"SELECT rq.ZONE_NAME, rq.ZONE_CODE, rq.COMM_NAME, rq.col1_7, rq.col1_8, \n" +
				"       rq.avsvl, rq.score_of_subparameter4c, mv.median_col1_7 AS median\n" +
				"FROM RankedQuery rq\n" +
				"CROSS JOIN MedianValue mv\n" +
				"WHERE rq.ZONE_CODE = 56 -- Filter for ZONE_CODE = 56\n" +
				"ORDER BY rq.score_of_subparameter4c DESC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst4c_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH FirstQuery AS (SELECT zc.ZONE_NAME,cc.ZONE_CODE,cc.COMM_NAME,14c.DETECTION_CGST_AMT + 14c.DETECTION_SGST_AMT + 14c.DETECTION_IGST_AMT + 14c.DETECTION_CESS_AMT AS col1_7\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_gi_gst_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 14c.MM_YYYY = '" + month_date + "' \n" +
				"), \n" +
				"SecondQuery AS (\n" +
				"SELECT zc.ZONE_NAME, cc.ZONE_CODE,cc.COMM_NAME,(7c.GROSS_TAX_CGST_FOR_C + 7c.GROSS_TAX_SGST_FOR_C + 7c.GROSS_TAX_IGST_FOR_C + 7c.GROSS_TAX_CESS_FOR_C) * 100 AS col1_8\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_ddm_gst_1 AS 7c ON cc.COMM_CODE = 7c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 7c.MM_YYYY = '" + month_date + "' \n" +
				"),\n" +
				"CombinedQuery AS (\n" +
				"SELECT \n" +
				"COALESCE(fq.ZONE_NAME, sq.ZONE_NAME) AS ZONE_NAME,\n" +
				"COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) AS ZONE_CODE,\n" +
				"COALESCE(fq.COMM_NAME, sq.COMM_NAME) AS COMM_NAME,\n" +
				"fq.col1_7, sq.col1_8,CONCAT((fq.col1_7), '/', (sq.col1_8)) AS avsvl,(fq.col1_7 * 100 / sq.col1_8) AS score_of_subparameter4c\n" +
				"FROM FirstQuery fq \n" +
				"LEFT JOIN \n" +
				"SecondQuery sq ON fq.ZONE_CODE = sq.ZONE_CODE AND fq.COMM_NAME = sq.COMM_NAME\n" +
				"WHERE COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) REGEXP '^[0-9]+$'\n" +
				"\n" +
				"UNION ALL\n" +
				"\n" +
				"SELECT \n" +
				"COALESCE(fq.ZONE_NAME, sq.ZONE_NAME) AS ZONE_NAME,\n" +
				"COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) AS ZONE_CODE,\n" +
				"COALESCE(fq.COMM_NAME, sq.COMM_NAME) AS COMM_NAME,\n" +
				"fq.col1_7, sq.col1_8,CONCAT((fq.col1_7), '/', (sq.col1_8)) AS avsvl,(fq.col1_7 * 100 / sq.col1_8) AS score_of_subparameter4c\n" +
				"FROM SecondQuery sq \n" +
				"LEFT JOIN \n" +
				"FirstQuery fq ON fq.ZONE_CODE = sq.ZONE_CODE AND fq.COMM_NAME = sq.COMM_NAME\n" +
				"WHERE \n" +
				"fq.ZONE_CODE IS NULL \n" +
				"AND COALESCE(fq.ZONE_CODE, sq.ZONE_CODE) REGEXP '^[0-9]+$'\n" +
				"),\n" +
				"RankedQuery AS (\n" +
				"SELECT *,ROW_NUMBER() OVER (ORDER BY col1_7 ASC) AS row_num,\n" +
				"COUNT(*) OVER () AS total_rows\n" +
				"FROM CombinedQuery\n" +
				"),\n" +
				"MedianValue AS (\n" +
				"SELECT \n" +
				"        CASE \n" +
				"            WHEN total_rows % 2 = 1 THEN \n" +
				"                (SELECT col1_7 FROM RankedQuery WHERE row_num = (total_rows + 1) / 2)\n" +
				"            ELSE \n" +
				"                (SELECT AVG(col1_7) FROM RankedQuery WHERE row_num IN (total_rows / 2, total_rows / 2 + 1))\n" +
				"        END AS median_col1_7\n" +
				"    FROM \n" +
				"        RankedQuery\n" +
				"    LIMIT 1\n" +
				")\n" +
				"SELECT rq.ZONE_NAME,rq.ZONE_CODE,rq.COMM_NAME,rq.col1_7, rq.col1_8,rq.avsvl,rq.score_of_subparameter4c,mv.median_col1_7 AS median\n" +
				"FROM RankedQuery rq\n" +
				"CROSS JOIN MedianValue mv\n" +
				"ORDER BY rq.score_of_subparameter4c DESC;\n";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst4d_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        SUM(14c.REALISATION_CGST_AMT + 14c.REALISATION_IGST_AMT + 14c.REALISATION_SGST_AMT + 14c.REALISATION_CESS_AMT) AS col6_1,\n"
				+ "        SUM(14c.DETECTION_CGST_AMT + 14c.DETECTION_SGST_AMT + 14c.DETECTION_IGST_AMT + 14c.DETECTION_CESS_AMT) AS col6_3\n"
				+ "    FROM\n"
				+ "        mis_gst_commcode AS cc\n"
				+ "        RIGHT JOIN mis_gi_gst_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "        LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE\n"
				+ "        14c.MM_YYYY = '" + month_date + "'  -- Replace with your month_date variable\n"
				+ "    GROUP BY\n"
				+ "        cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "ranked_cte AS (\n"
				+ "    SELECT\n"
				+ "        ZONE_CODE,\n"
				+ "        ZONE_NAME,\n"
				+ "        col6_1,\n"
				+ "        col6_3,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY col6_1) AS row_num,\n"
				+ "        COUNT(*) OVER () AS total_rows\n"
				+ "    FROM\n"
				+ "        cte\n"
				+ "),\n"
				+ "median_cte AS (\n"
				+ "    SELECT\n"
				+ "        ZONE_CODE,\n"
				+ "        ZONE_NAME,\n"
				+ "        col6_1,\n"
				+ "        col6_3,\n"
				+ "        CASE\n"
				+ "            WHEN MOD(total_rows, 2) = 1 THEN  -- Odd number of rows\n"
				+ "                (SELECT col6_1 FROM ranked_cte WHERE row_num = (total_rows DIV 2) + 1)\n"
				+ "            ELSE  -- Even number of rows\n"
				+ "                (SELECT AVG(col6_1) FROM ranked_cte WHERE row_num IN ((total_rows DIV 2), (total_rows DIV 2) + 1))\n"
				+ "        END AS median_4d\n"
				+ "    FROM\n"
				+ "        ranked_cte\n"
				+ ")\n"
				+ "SELECT\n"
				+ "    ZONE_CODE,\n"
				+ "    ZONE_NAME,\n"
				+ "    col6_1,\n"
				+ "    col6_3,\n"
				+ "    CASE\n"
				+ "        WHEN col6_3 = 0 THEN 0\n"
				+ "        ELSE col6_1 * 100 / col6_3\n"
				+ "    END AS total_score,\n"
				+ "    median_4d,\n"
				+ "    CONCAT(col6_1, '/', col6_3) AS absval  -- Add absval in p/q form\n"
				+ "FROM\n"
				+ "    median_cte\n"
				+ "ORDER BY\n"
				+ "    total_score DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst4d_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH RankedData AS (\n"
				+ "    SELECT\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.COMM_NAME,\n"
				+ "        14c.REALISATION_CGST_AMT + 14c.REALISATION_IGST_AMT + 14c.REALISATION_SGST_AMT + 14c.REALISATION_CESS_AMT AS col6_1,\n"
				+ "        14c.DETECTION_CGST_AMT + 14c.DETECTION_SGST_AMT + 14c.DETECTION_IGST_AMT + 14c.DETECTION_CESS_AMT AS col6_3,\n"
				+ "        (\n"
				+ "            (14c.REALISATION_CGST_AMT + 14c.REALISATION_IGST_AMT + 14c.REALISATION_SGST_AMT + 14c.REALISATION_CESS_AMT) / \n"
				+ "            (14c.DETECTION_CGST_AMT + 14c.DETECTION_SGST_AMT + 14c.DETECTION_IGST_AMT + 14c.DETECTION_CESS_AMT) * 100 \n"
				+ "        ) AS total_score\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_gi_gst_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 14c.MM_YYYY ='" + month_date + "'\n"
				+ "),\n"
				+ "MedianData AS (\n"
				+ "    SELECT \n"
				+ "        col6_1,\n"
				+ "        COUNT(*) OVER () AS total_rows,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY col6_1) AS rn\n"
				+ "    FROM RankedData\n"
				+ ")\n"
				+ "\n"
				+ "SELECT \n"
				+ "    rd.ZONE_CODE,\n"
				+ "    rd.ZONE_NAME,\n"
				+ "    rd.COMM_NAME,\n"
				+ "    rd.col6_1,\n"
				+ "    rd.col6_3,\n"
				+ "    rd.total_score,\n"
				+ "    CASE \n"
				+ "        WHEN md.total_rows % 2 = 1 THEN\n"
				+ "            (SELECT col6_1 FROM MedianData WHERE rn = (md.total_rows + 1) / 2)\n"
				+ "        ELSE\n"
				+ "            (SELECT AVG(col6_1) \n"
				+ "             FROM MedianData \n"
				+ "             WHERE rn IN (md.total_rows / 2, md.total_rows / 2 + 1))\n"
				+ "    END AS median_4d,\n"
				+ "    CONCAT(rd.col6_1, '/', rd.col6_3) AS absvl\n"
				+ "FROM RankedData rd\n"
				+ "JOIN (SELECT COUNT(*) AS total_rows FROM MedianData) md ON 1=1\n"
				+ "WHERE rd.ZONE_CODE = '" + zone_code + "'  -- Added condition to filter by ZONE_CODE\n"
				+ "ORDER BY total_score DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst4d_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH RankedData AS (\n"
				+ "    SELECT\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.COMM_NAME,\n"
				+ "        14c.REALISATION_CGST_AMT + 14c.REALISATION_IGST_AMT + 14c.REALISATION_SGST_AMT + 14c.REALISATION_CESS_AMT AS col6_1,\n"
				+ "        14c.DETECTION_CGST_AMT + 14c.DETECTION_SGST_AMT + 14c.DETECTION_IGST_AMT + 14c.DETECTION_CESS_AMT AS col6_3,\n"
				+ "        (\n"
				+ "            (14c.REALISATION_CGST_AMT + 14c.REALISATION_IGST_AMT + 14c.REALISATION_SGST_AMT + 14c.REALISATION_CESS_AMT) / \n"
				+ "            (14c.DETECTION_CGST_AMT + 14c.DETECTION_SGST_AMT + 14c.DETECTION_IGST_AMT + 14c.DETECTION_CESS_AMT) * 100 \n"
				+ "        ) AS total_score\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_gi_gst_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 14c.MM_YYYY = '" + month_date + "'\n"
				+ "),\n"
				+ "MedianData AS (\n"
				+ "    SELECT \n"
				+ "        col6_1,\n"
				+ "        COUNT(*) OVER () AS total_rows,\n"
				+ "        ROW_NUMBER() OVER (ORDER BY col6_1) AS rn\n"
				+ "    FROM RankedData\n"
				+ ")\n"
				+ "\n"
				+ "SELECT \n"
				+ "    rd.ZONE_CODE,\n"
				+ "    rd.ZONE_NAME,\n"
				+ "    rd.COMM_NAME,\n"
				+ "    rd.col6_1,\n"
				+ "    rd.col6_3,\n"
				+ "    rd.total_score,\n"
				+ "    CASE \n"
				+ "        WHEN md.total_rows % 2 = 1 THEN\n"
				+ "            (SELECT col6_1 FROM MedianData WHERE rn = (md.total_rows + 1) / 2)\n"
				+ "        ELSE\n"
				+ "            (SELECT AVG(col6_1) \n"
				+ "             FROM MedianData \n"
				+ "             WHERE rn IN (md.total_rows / 2, md.total_rows / 2 + 1))\n"
				+ "    END AS median_4d,\n"
				+ "    CONCAT(rd.col6_1, '/', rd.col6_3) AS absvl\n"
				+ "FROM RankedData rd\n"
				+ "JOIN (SELECT COUNT(*) AS total_rows FROM MedianData) md ON 1=1\n"
				+ "ORDER BY total_score DESC;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst5a_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH ranked_data AS (\n" +
				"    SELECT zc.ZONE_NAME, cc.ZONE_CODE,\n" +
				"        SUM(14c.adc_commissionerate_disposal_no + 14c.adc_audit_disposal_no + 14c.adc_investigation_disposal_no + 14c.adc_callbook_disposal_no + \n" +
				"            14c.dc_commissionerate_disposal_no + 14c.dc_audit_disposal_no + 14c.dc_investigation_disposal_no + 14c.dc_callbook_disposal_no + \n" +
				"            14c.superintendent_commissionerate_disposal_no + 14c.superintendent_audit_disposal_no + 14c.superintendent_investigation_disposal_no + 14c.superintendent_callbook_disposal_no\n" +
				"        ) AS col10,\n" +
				"        SUM(14c.ADC_COMMISSIONERATE_OPENING_NO + 14c.ADC_AUDIT_OPENING_NO + 14c.ADC_INVESTIGATION_OPENING_NO + 14c.ADC_CALLBOOK_OPENING_NO + \n" +
				"            14c.DC_COMMISSIONERATE_OPENING_NO + 14c.DC_AUDIT_OPENING_NO + 14c.DC_INVESTIGATION_OPENING_NO + 14c.DC_CALLBOOK_OPENING_NO + \n" +
				"            14c.SUPERINTENDENT_COMMISSIONERATE_OPENING_NO + 14c.SUPERINTENDENT_AUDIT_OPENING_NO + 14c.SUPERINTENDENT_INVESTIGATION_OPENING_NO + 14c.SUPERINTENDENT_CALLBOOK_OPENING_NO\n" +
				"        ) AS col4,\n" +
				"        CASE \n" +
				"            WHEN SUM(14c.ADC_COMMISSIONERATE_OPENING_NO + 14c.ADC_AUDIT_OPENING_NO + 14c.ADC_INVESTIGATION_OPENING_NO + 14c.ADC_CALLBOOK_OPENING_NO + \n" +
				"                14c.DC_COMMISSIONERATE_OPENING_NO + 14c.DC_AUDIT_OPENING_NO + 14c.DC_INVESTIGATION_OPENING_NO + 14c.DC_CALLBOOK_OPENING_NO + \n" +
				"                14c.SUPERINTENDENT_COMMISSIONERATE_OPENING_NO + 14c.SUPERINTENDENT_AUDIT_OPENING_NO + 14c.SUPERINTENDENT_INVESTIGATION_OPENING_NO + 14c.SUPERINTENDENT_CALLBOOK_OPENING_NO\n" +
				"            ) = 0 THEN NULL\n" +
				"            ELSE SUM(14c.adc_commissionerate_disposal_no + 14c.adc_audit_disposal_no + 14c.adc_investigation_disposal_no + 14c.adc_callbook_disposal_no + \n" +
				"                14c.dc_commissionerate_disposal_no + 14c.dc_audit_disposal_no + 14c.dc_investigation_disposal_no + 14c.dc_callbook_disposal_no + \n" +
				"                14c.superintendent_commissionerate_disposal_no + 14c.superintendent_audit_disposal_no + 14c.superintendent_investigation_disposal_no + 14c.superintendent_callbook_disposal_no\n" +
				"            ) / \n" +
				"            SUM(14c.ADC_COMMISSIONERATE_OPENING_NO + 14c.ADC_AUDIT_OPENING_NO + 14c.ADC_INVESTIGATION_OPENING_NO + 14c.ADC_CALLBOOK_OPENING_NO + \n" +
				"                14c.DC_COMMISSIONERATE_OPENING_NO + 14c.DC_AUDIT_OPENING_NO + 14c.DC_INVESTIGATION_OPENING_NO + 14c.DC_CALLBOOK_OPENING_NO + \n" +
				"                14c.SUPERINTENDENT_COMMISSIONERATE_OPENING_NO + 14c.SUPERINTENDENT_AUDIT_OPENING_NO + 14c.SUPERINTENDENT_INVESTIGATION_OPENING_NO + 14c.SUPERINTENDENT_CALLBOOK_OPENING_NO\n" +
				"            ) * 100\n" +
				"        END AS score_of_subparameter5a\n" +
				"    FROM mis_gst_commcode AS cc\n" +
				"    RIGHT JOIN mis_dpm_gst_adj_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE 14c.MM_YYYY = '" + month_date + "'\n" +
				"    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME\n" +
				"),\n" +
				"ranked_col10 AS (\n" +
				"    SELECT col10,@row_num := @row_num + 1 AS row_num,@total_rows AS total_rows\n" +
				"    FROM ranked_data\n" +
				"    CROSS JOIN (SELECT @row_num := 0, @total_rows := (SELECT COUNT(*) FROM ranked_data WHERE col10 IS NOT NULL)) AS vars\n" +
				"    WHERE col10 IS NOT NULL\n" +
				"    ORDER BY col10\n" +
				"),\n" +
				"median_data AS (\n" +
				"    SELECT AVG(col10) AS median_col10\n" +
				"    FROM ranked_col10\n" +
				"    WHERE row_num IN (FLOOR((total_rows + 1) / 2), CEIL((total_rows + 1) / 2))\n" +
				")\n" +
				"SELECT rd.ZONE_NAME,rd.ZONE_CODE,rd.col10,rd.col4,rd.score_of_subparameter5a,\n" +
				"    DENSE_RANK() OVER (ORDER BY rd.score_of_subparameter5a DESC) AS z_rank,\n" +
				"    md.median_col10 AS median5a\n" +
				"FROM ranked_data rd\n" +
				"CROSS JOIN median_data md;";
		return queryGst14aa;
	}
	public String QueryFor_gst5a_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH cte AS (\n" +
				"    SELECT zc.ZONE_NAME, cc.COMM_NAME, cc.ZONE_CODE,\n" +
				"    (14c.adc_commissionerate_disposal_no + 14c.adc_audit_disposal_no + 14c.adc_investigation_disposal_no + \n" +
				"    14c.adc_callbook_disposal_no + 14c.dc_commissionerate_disposal_no + 14c.dc_audit_disposal_no + \n" +
				"    14c.dc_investigation_disposal_no + 14c.dc_callbook_disposal_no + \n" +
				"    14c.superintendent_commissionerate_disposal_no + 14c.superintendent_audit_disposal_no +\n" +
				"    14c.superintendent_investigation_disposal_no + 14c.superintendent_callbook_disposal_no) as numerator_5a, -- col10\n" +
				"    (14c.ADC_COMMISSIONERATE_OPENING_NO + 14c.ADC_AUDIT_OPENING_NO + 14c.ADC_INVESTIGATION_OPENING_NO + \n" +
				"    14c.ADC_CALLBOOK_OPENING_NO + 14c.DC_COMMISSIONERATE_OPENING_NO + 14c.DC_AUDIT_OPENING_NO + \n" +
				"    14c.DC_INVESTIGATION_OPENING_NO + 14c.DC_CALLBOOK_OPENING_NO + \n" +
				"    14c.SUPERINTENDENT_COMMISSIONERATE_OPENING_NO + 14c.SUPERINTENDENT_AUDIT_OPENING_NO + \n" +
				"    14c.SUPERINTENDENT_INVESTIGATION_OPENING_NO + 14c.SUPERINTENDENT_CALLBOOK_OPENING_NO) as col4\n" +
				"    FROM mis_gst_commcode as cc\n" +
				"    RIGHT JOIN mis_dpm_gst_adj_1 as 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode as zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE 14c.MM_YYYY = '" + month_date + "'\n" +
				"),\n" +
				"median_cte AS (\n" +
				"    SELECT numerator_5a, ROW_NUMBER() OVER (ORDER BY numerator_5a) as rn,\n" +
				"        COUNT(*) OVER () as cnt\n" +
				"    FROM cte\n" +
				"),\n" +
				"median_value AS (\n" +
				"    SELECT AVG(numerator_5a) as median_5a\n" +
				"    FROM median_cte\n" +
				"    WHERE rn IN (FLOOR((cnt + 1) / 2), CEIL((cnt + 1) / 2))\n" +
				")\n" +
				"SELECT ZONE_NAME, COMM_NAME, ZONE_CODE, numerator_5a,col4 ,\n" +
				"    CASE WHEN col4 = 0 THEN 0 ELSE numerator_5a * 100 / col4 END AS score_of_subparameter_5a,\n" +
				"    (SELECT median_5a FROM median_value) as median_5a,\n" +
				"    CONCAT(numerator_5a, '/', col4) as absvl_5a\n" +
				"FROM cte\n" +
				"WHERE ZONE_CODE = '" + zone_code + "';";
		return queryGst14aa;
	}
	public String QueryFor_gst5a_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH cte AS (\n" +
				"    SELECT zc.ZONE_NAME,cc.COMM_NAME,cc.ZONE_CODE,\n" +
				"        (14c.adc_commissionerate_disposal_no + 14c.adc_audit_disposal_no + 14c.adc_investigation_disposal_no + \n" +
				"         14c.adc_callbook_disposal_no + 14c.dc_commissionerate_disposal_no + 14c.dc_audit_disposal_no + \n" +
				"         14c.dc_investigation_disposal_no + 14c.dc_callbook_disposal_no + \n" +
				"         14c.superintendent_commissionerate_disposal_no + 14c.superintendent_audit_disposal_no + \n" +
				"         14c.superintendent_investigation_disposal_no + 14c.superintendent_callbook_disposal_no) as col10,\n" +
				"        (14c.ADC_COMMISSIONERATE_OPENING_NO + 14c.ADC_AUDIT_OPENING_NO + 14c.ADC_INVESTIGATION_OPENING_NO + \n" +
				"         14c.ADC_CALLBOOK_OPENING_NO + 14c.DC_COMMISSIONERATE_OPENING_NO + 14c.DC_AUDIT_OPENING_NO + \n" +
				"         14c.DC_INVESTIGATION_OPENING_NO + 14c.DC_CALLBOOK_OPENING_NO + \n" +
				"         14c.SUPERINTENDENT_COMMISSIONERATE_OPENING_NO + 14c.SUPERINTENDENT_AUDIT_OPENING_NO + \n" +
				"         14c.SUPERINTENDENT_INVESTIGATION_OPENING_NO + 14c.SUPERINTENDENT_CALLBOOK_OPENING_NO) as col4\n" +
				"    FROM mis_gst_commcode as cc\n" +
				"    RIGHT JOIN mis_dpm_gst_adj_1 as 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode as zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE 14c.MM_YYYY = '" + month_date + "'\n" +
				"),\n" +
				"median_cte AS (\n" +
				"    SELECT col10,ROW_NUMBER() OVER (ORDER BY col10) as rn,\n" +
				"        COUNT(*) OVER () as cnt\n" +
				"    FROM cte\n" +
				"),\n" +
				"median_value AS (\n" +
				"    SELECT AVG(col10) as median\n" +
				"    FROM median_cte\n" +
				"    WHERE rn IN (FLOOR((cnt + 1) / 2), CEIL((cnt + 1) / 2))\n" +
				")\n" +
				"SELECT ZONE_NAME,COMM_NAME,ZONE_CODE,col10,col4,\n" +
				"    CASE WHEN col4 = 0 THEN 0 ELSE col10 * 100 / col4 \n" +
				"    END AS score_of_subparameter,\n" +
				"    (SELECT median FROM median_value) as median\n" +
				"FROM cte\n" +
				"ORDER BY score_of_subparameter DESC;\n";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst5b_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String next_month_new = DateCalculate.getNextMonth(month_date);

		String queryGst14aa= "WITH Query1 AS (\n"
				+ "    SELECT zc.ZONE_NAME, cc.ZONE_CODE,\n"
				+ "        SUM(14c.ADC_COMMISSIONERATE_TIME_LESS_3_NO +14c.ADC_AUDIT_TIME_LESS_3_NO +14c.ADC_INVESTIGATION_TIME_LESS_3_NO +14c.ADC_CALLBOOK_TIME_LESS_3_NO +\n"
				+ "            14c.DC_COMMISSIONERATE_TIME_LESS_3_NO +14c.DC_AUDIT_TIME_LESS_3_NO +14c.DC_INVESTIGATION_TIME_LESS_3_NO +14c.DC_CALLBOOK_TIME_LESS_3_NO +\n"
				+ "            14c.SUPERINTENDENT_COMMISSIONERATE_TIME_LESS_3_NO +14c.SUPERINTENDENT_AUDIT_TIME_LESS_3_NO +14c.SUPERINTENDENT_INVESTIGATION_TIME_LESS_3_NO +14c.SUPERINTENDENT_CALLBOOK_TIME_LESS_3_NO\n"
				+ "        ) AS col22,\n"
				+ "        SUM(14c.ADC_COMMISSIONERATE_TIME_3_TO_6_NO +14c.ADC_AUDIT_TIME_3_TO_6_NO +14c.ADC_INVESTIGATION_TIME_3_TO_6_NO +14c.ADC_CALLBOOK_TIME_3_TO_6_NO +\n"
				+ "            14c.DC_COMMISSIONERATE_TIME_3_TO_6_NO +14c.DC_AUDIT_TIME_3_TO_6_NO +14c.DC_INVESTIGATION_TIME_3_TO_6_NO +14c.DC_CALLBOOK_TIME_3_TO_6_NO +\n"
				+ "            14c.SUPERINTENDENT_COMMISSIONERATE_TIME_3_TO_6_NO +14c.SUPERINTENDENT_AUDIT_TIME_3_TO_6_NO +14c.SUPERINTENDENT_INVESTIGATION_TIME_3_TO_6_NO +14c.SUPERINTENDENT_CALLBOOK_TIME_3_TO_6_NO\n"
				+ "        ) AS col23\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_dpm_gst_adj_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 14c.MM_YYYY = '" + month_date + "'  GROUP BY cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "Query2 AS (\n"
				+ "    SELECT zc.ZONE_NAME, cc.ZONE_CODE,\n"
				+ "        SUM(14c.ADC_COMMISSIONERATE_OPENING_NO +14c.ADC_AUDIT_OPENING_NO +14c.ADC_INVESTIGATION_OPENING_NO +14c.ADC_CALLBOOK_OPENING_NO +14c.DC_COMMISSIONERATE_OPENING_NO +\n"
				+ "            14c.DC_AUDIT_OPENING_NO +14c.DC_INVESTIGATION_OPENING_NO +14c.DC_CALLBOOK_OPENING_NO +14c.SUPERINTENDENT_COMMISSIONERATE_OPENING_NO +\n"
				+ "            14c.SUPERINTENDENT_AUDIT_OPENING_NO +14c.SUPERINTENDENT_INVESTIGATION_OPENING_NO +14c.SUPERINTENDENT_CALLBOOK_OPENING_NO\n"
				+ "        ) AS col16\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_dpm_gst_adj_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 14c.MM_YYYY = '" + next_month_new + "' GROUP BY cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "RankedData AS (\n"
				+ "    SELECT q1.ZONE_NAME, q1.ZONE_CODE,\n"
				+ "        COALESCE(q1.col22, 0) AS col22,COALESCE(q1.col23, 0) AS col23,COALESCE(q2.col16, 0) AS col16,\n"
				+ "        CASE\n"
				+ "            WHEN COALESCE(q2.col16, 0) = 0 THEN 0\n"
				+ "            ELSE ((COALESCE(q1.col22, 0) + COALESCE(q1.col23, 0)) / COALESCE(q2.col16, 0)) * 100\n"
				+ "        END AS total_score\n"
				+ "    FROM Query1 AS q1\n"
				+ "    LEFT JOIN \n"
				+ "        Query2 AS q2 ON q1.ZONE_CODE = q2.ZONE_CODE\n"
				+ ")\n"
				+ "SELECT ZONE_NAME,ZONE_CODE, col22, col23, col16,total_score,\n"
				+ "    DENSE_RANK() OVER (ORDER BY total_score ASC) AS z_rank FROM RankedData;";
		return queryGst14aa;
	}
	public String QueryFor_gst5b_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String next_month_new = DateCalculate.getNextMonth(month_date);
		String queryGst14aa="WITH CTE1 AS (\n" +
				"SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME,\n" +
				"\t(14c.ADC_COMMISSIONERATE_TIME_LESS_3_NO +14c.ADC_AUDIT_TIME_LESS_3_NO +14c.ADC_INVESTIGATION_TIME_LESS_3_NO +14c.ADC_CALLBOOK_TIME_LESS_3_NO +14c.DC_COMMISSIONERATE_TIME_LESS_3_NO +14c.DC_AUDIT_TIME_LESS_3_NO +\n" +
				"\t14c.DC_INVESTIGATION_TIME_LESS_3_NO +14c.DC_CALLBOOK_TIME_LESS_3_NO +14c.SUPERINTENDENT_COMMISSIONERATE_TIME_LESS_3_NO +14c.SUPERINTENDENT_AUDIT_TIME_LESS_3_NO +14c.SUPERINTENDENT_INVESTIGATION_TIME_LESS_3_NO +\n" +
				"\t14c.SUPERINTENDENT_CALLBOOK_TIME_LESS_3_NO) AS col22, \n" +
				"    (14c.ADC_COMMISSIONERATE_TIME_3_TO_6_NO +14c.ADC_AUDIT_TIME_3_TO_6_NO +14c.ADC_INVESTIGATION_TIME_3_TO_6_NO +14c.ADC_CALLBOOK_TIME_3_TO_6_NO +14c.DC_COMMISSIONERATE_TIME_3_TO_6_NO +14c.DC_AUDIT_TIME_3_TO_6_NO +\n" +
				"    14c.DC_INVESTIGATION_TIME_3_TO_6_NO +14c.DC_CALLBOOK_TIME_3_TO_6_NO +14c.SUPERINTENDENT_COMMISSIONERATE_TIME_3_TO_6_NO +14c.SUPERINTENDENT_AUDIT_TIME_3_TO_6_NO +14c.SUPERINTENDENT_INVESTIGATION_TIME_3_TO_6_NO +\n" +
				"    14c.SUPERINTENDENT_CALLBOOK_TIME_3_TO_6_NO) AS col23, \n" +
				"        NULL AS col16\n" +
				"    FROM mis_gst_commcode AS cc \n" +
				"    RIGHT JOIN mis_dpm_gst_adj_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE WHERE 14c.MM_YYYY = '" +  month_date  + "' and cc.ZONE_CODE = '" + zone_code + "'),\n" +
				"CTE2 AS (\n" +
				"    SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME,NULL AS col22, NULL AS col23,\n" +
				"    (14c.ADC_COMMISSIONERATE_OPENING_NO +14c.ADC_AUDIT_OPENING_NO +14c.ADC_INVESTIGATION_OPENING_NO +14c.ADC_CALLBOOK_OPENING_NO +14c.DC_COMMISSIONERATE_OPENING_NO +14c.DC_AUDIT_OPENING_NO +\n" +
				"\t14c.DC_INVESTIGATION_OPENING_NO +14c.DC_CALLBOOK_OPENING_NO +14c.SUPERINTENDENT_COMMISSIONERATE_OPENING_NO +14c.SUPERINTENDENT_AUDIT_OPENING_NO +14c.SUPERINTENDENT_INVESTIGATION_OPENING_NO +14c.SUPERINTENDENT_CALLBOOK_OPENING_NO) AS col16\n" +
				"    FROM mis_gst_commcode AS cc \n" +
				"    RIGHT JOIN mis_dpm_gst_adj_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE WHERE 14c.MM_YYYY = '" + next_month_new + "' and cc.ZONE_CODE = '" + zone_code + "')\n" +
				"    SELECT ZONE_NAME, ZONE_CODE, COMM_NAME, SUM(col22) AS col22, SUM(col23) AS col23, SUM(col16) AS col16,\n" +
				"    (( SUM(col22) + SUM(col23) ) * 100 / SUM(col16)) as score_of_parameter\n" +
				"    FROM (SELECT * FROM CTE1\n" +
				"\tUNION ALL\n" +
				"    SELECT * FROM CTE2) AS combined_data GROUP BY ZONE_NAME, ZONE_CODE, COMM_NAME " +
				"order by score_of_parameter;";
		return queryGst14aa;
	}
	public String QueryFor_gst5b_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String next_month_new = DateCalculate.getNextMonth(month_date);
		String queryGst14aa="WITH CTE1 AS (\n" +
				"SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME,\n" +
				"\t(14c.ADC_COMMISSIONERATE_TIME_LESS_3_NO +14c.ADC_AUDIT_TIME_LESS_3_NO +14c.ADC_INVESTIGATION_TIME_LESS_3_NO +14c.ADC_CALLBOOK_TIME_LESS_3_NO +14c.DC_COMMISSIONERATE_TIME_LESS_3_NO +14c.DC_AUDIT_TIME_LESS_3_NO +\n" +
				"\t14c.DC_INVESTIGATION_TIME_LESS_3_NO +14c.DC_CALLBOOK_TIME_LESS_3_NO +14c.SUPERINTENDENT_COMMISSIONERATE_TIME_LESS_3_NO +14c.SUPERINTENDENT_AUDIT_TIME_LESS_3_NO +14c.SUPERINTENDENT_INVESTIGATION_TIME_LESS_3_NO +\n" +
				"\t14c.SUPERINTENDENT_CALLBOOK_TIME_LESS_3_NO) AS col22, \n" +
				"    (14c.ADC_COMMISSIONERATE_TIME_3_TO_6_NO +14c.ADC_AUDIT_TIME_3_TO_6_NO +14c.ADC_INVESTIGATION_TIME_3_TO_6_NO +14c.ADC_CALLBOOK_TIME_3_TO_6_NO +14c.DC_COMMISSIONERATE_TIME_3_TO_6_NO +14c.DC_AUDIT_TIME_3_TO_6_NO +\n" +
				"    14c.DC_INVESTIGATION_TIME_3_TO_6_NO +14c.DC_CALLBOOK_TIME_3_TO_6_NO +14c.SUPERINTENDENT_COMMISSIONERATE_TIME_3_TO_6_NO +14c.SUPERINTENDENT_AUDIT_TIME_3_TO_6_NO +14c.SUPERINTENDENT_INVESTIGATION_TIME_3_TO_6_NO +\n" +
				"    14c.SUPERINTENDENT_CALLBOOK_TIME_3_TO_6_NO) AS col23, \n" +
				"        NULL AS col16\n" +
				"    FROM mis_gst_commcode AS cc \n" +
				"    RIGHT JOIN mis_dpm_gst_adj_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE WHERE 14c.MM_YYYY = '" +  month_date  + "'),\n" +
				"CTE2 AS (\n" +
				"    SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME,NULL AS col22, NULL AS col23,\n" +
				"    (14c.ADC_COMMISSIONERATE_OPENING_NO +14c.ADC_AUDIT_OPENING_NO +14c.ADC_INVESTIGATION_OPENING_NO +14c.ADC_CALLBOOK_OPENING_NO +14c.DC_COMMISSIONERATE_OPENING_NO +14c.DC_AUDIT_OPENING_NO +\n" +
				"\t14c.DC_INVESTIGATION_OPENING_NO +14c.DC_CALLBOOK_OPENING_NO +14c.SUPERINTENDENT_COMMISSIONERATE_OPENING_NO +14c.SUPERINTENDENT_AUDIT_OPENING_NO +14c.SUPERINTENDENT_INVESTIGATION_OPENING_NO +14c.SUPERINTENDENT_CALLBOOK_OPENING_NO) AS col16\n" +
				"    FROM mis_gst_commcode AS cc \n" +
				"    RIGHT JOIN mis_dpm_gst_adj_1 AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE WHERE 14c.MM_YYYY = '" + next_month_new + "')\n" +
				"    SELECT ZONE_NAME, ZONE_CODE, COMM_NAME, SUM(col22) AS col22, SUM(col23) AS col23, SUM(col16) AS col16,\n" +
				"    (( SUM(col22) + SUM(col23) ) * 100 / SUM(col16)) as score_of_parameter\n" +
				"    FROM (SELECT * FROM CTE1\n" +
				"\tUNION ALL\n" +
				"    SELECT * FROM CTE2) AS combined_data GROUP BY ZONE_NAME, ZONE_CODE, COMM_NAME " +
				"order by score_of_parameter;";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst6a_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH col9_data AS (\n" +
				"    SELECT zc.ZONE_NAME,cc.ZONE_CODE,SUM(14c.COMM_DISPOSAL_NO + 14c.JC_DISPOSAL_NO + 14c.AC_DISPOSAL_NO + 14c.SUP_DISPOSAL_NO) AS col9\n" +
				"    FROM mis_gst_commcode AS cc\n" +
				"    RIGHT JOIN mis_dgi_st_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE 14c.MM_YYYY = '" + month_date + "'\n" +
				"    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME\n" +
				"),\n" +
				"col3_data AS (\n" +
				"    SELECT zc.ZONE_NAME,cc.ZONE_CODE,SUM(14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col3\n" +
				"    FROM mis_gst_commcode AS cc\n" +
				"    RIGHT JOIN mis_dgi_st_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE 14c.MM_YYYY = '" + prev_month_new + "'\n" +
				"    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME\n" +
				"),\n" +
				"ranked_data AS (\n" +
				"    SELECT col9_data.ZONE_NAME,col9_data.ZONE_CODE,col9_data.col9,col3_data.col3,\n" +
				"        CASE\n" +
				"\t\tWHEN col3_data.col3 = 0 THEN 0\n" +
				"            ELSE (col9_data.col9 / col3_data.col3) * 100\n" +
				"        END AS total_score\n" +
				"    FROM col9_data\n" +
				"    LEFT JOIN col3_data ON col9_data.ZONE_CODE = col3_data.ZONE_CODE AND col9_data.ZONE_NAME = col3_data.ZONE_NAME\n" +
				"),\n" +
				"median_calc AS (\n" +
				"    SELECT col9,ROW_NUMBER() OVER (ORDER BY col9) AS row_num,COUNT(*) OVER () AS total_rows\n" +
				"    FROM ranked_data\n" +
				"),\n" +
				"median_value AS (\n" +
				"    SELECT AVG(col9) AS median\n" +
				"    FROM median_calc\n" +
				"    WHERE row_num IN (FLOOR((total_rows + 1) / 2), FLOOR((total_rows + 2) / 2))\n" +
				")\n" +
				"SELECT r.ZONE_NAME,r.ZONE_CODE,r.col9,r.col3,r.total_score,m.median,\n" +
				"    RANK() OVER (ORDER BY r.total_score DESC) AS z_rank\n" +
				"FROM ranked_data r\n" +
				"CROSS JOIN median_value m\n" +
				"WHERE r.ZONE_NAME NOT IN ('DG East', 'CEI DG');\n";
		return queryGst14aa;
	}
	public String QueryFor_gst6a_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);

		String queryGst14aa="WITH cte1 AS (\n" +
				"SELECT zc.ZONE_NAME,cc.ZONE_CODE,cc.COMM_NAME, (14c.COMM_DISPOSAL_NO + 14c.JC_DISPOSAL_NO + 14c.AC_DISPOSAL_NO + 14c.SUP_DISPOSAL_NO) AS col9\n" +
				"FROM mis_gst_commcode AS cc \n" +
				"RIGHT JOIN mis_dgi_st_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE \n" +
				"WHERE 14c.MM_YYYY = '" + month_date + "' AND zc.ZONE_NAME NOT IN ('DG East', 'CEI DG')\n" +
				"), \n" +
				"cte2 AS (\n" +
				"SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME, (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col3\n" +
				"FROM mis_gst_commcode AS cc \n" +
				"RIGHT JOIN mis_dgi_st_1a AS 14c \n" +
				"ON cc.COMM_CODE = 14c.COMM_CODE \n" +
				"LEFT JOIN mis_gst_zonecode AS zc \n" +
				"ON zc.ZONE_CODE = cc.ZONE_CODE \n" +
				"WHERE 14c.MM_YYYY = '" + prev_month_new + "' AND zc.ZONE_NAME NOT IN ('DG East', 'CEI DG')\n" +
				"), \n" +
				"final_data AS (\n" +
				"SELECT cte1.ZONE_NAME, cte1.ZONE_CODE, cte1.COMM_NAME, cte1.col9, cte2.col3, cte1.col9 AS numerator_6a, \n" +
				"CASE WHEN cte2.col3 = 0 THEN 0 \n" +
				"ELSE (cte1.col9 / cte2.col3) * 100 \n" +
				"END AS total_score \n" +
				"FROM cte1 \n" +
				"JOIN cte2 \n" +
				"ON cte1.ZONE_NAME = cte2.ZONE_NAME AND cte1.ZONE_CODE = cte2.ZONE_CODE AND cte1.COMM_NAME = cte2.COMM_NAME\n" +
				"), \n" +
				"ranked_data AS (\n" +
				"SELECT final_data.*,\n" +
				"ROW_NUMBER() OVER (ORDER BY final_data.numerator_6a) AS row_num, COUNT(*) OVER () AS total_rows FROM final_data\n" +
				")\n" +
				"SELECT ranked_data.ZONE_NAME, ranked_data.ZONE_CODE, ranked_data.COMM_NAME, ranked_data.col9, ranked_data.col3, ranked_data.total_score, ranked_data.numerator_6a,\n" +
				"CASE \n" +
				"WHEN total_rows % 2 = 1 THEN (SELECT numerator_6a FROM ranked_data WHERE row_num = (total_rows + 1) / 2) \n" +
				"ELSE (SELECT AVG(numerator_6a) FROM ranked_data WHERE row_num IN (total_rows / 2, (total_rows / 2) + 1)) \n" +
				"END AS median_numerator_6a, \n" +
				"DENSE_RANK() OVER (ORDER BY total_score DESC) AS z_rank \n" +
				"FROM ranked_data\n" +
				"WHERE ranked_data.ZONE_CODE = '" + zone_code + "';";
//        String queryGst14aa="WITH cte1 AS (\n" +
//                "    SELECT zc.ZONE_NAME, cc.COMM_NAME, cc.ZONE_CODE, \n" +
//                "           (14c.COMM_DISPOSAL_NO + 14c.JC_DISPOSAL_NO + 14c.AC_DISPOSAL_NO + 14c.SUP_DISPOSAL_NO) AS col9\n" +
//                "    FROM mis_gst_commcode AS cc\n" +
//                "    RIGHT JOIN mis_dgi_st_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
//                "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
//                "    WHERE cc.ZONE_CODE = '" + zone_code + "' \n" +
//                "      AND 14c.MM_YYYY = '" + month_date + "'\n" +
//                "),\n" +
//                "cte2 AS (\n" +
//                "    SELECT zc.ZONE_NAME, cc.COMM_NAME, cc.ZONE_CODE, \n" +
//                "           (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col3\n" +
//                "    FROM mis_gst_commcode AS cc\n" +
//                "    RIGHT JOIN mis_dgi_st_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
//                "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
//                "    WHERE cc.ZONE_CODE = '" + zone_code + "' \n" +
//                "      AND 14c.MM_YYYY = '" + prev_month_new + "'\n" +
//                "),\n" +
//                "ranked_data AS (\n" +
//                "    SELECT cte1.ZONE_NAME, cte1.COMM_NAME, cte1.ZONE_CODE, cte1.col9, cte2.col3,\n" +
//                "           (CASE WHEN cte2.col3 != 0 THEN (cte1.col9 / cte2.col3) * 100 ELSE NULL END) AS total_score\n" +
//                "    FROM cte1\n" +
//                "    LEFT JOIN cte2 \n" +
//                "    ON cte1.ZONE_NAME = cte2.ZONE_NAME \n" +
//                "       AND cte1.COMM_NAME = cte2.COMM_NAME \n" +
//                "       AND cte1.ZONE_CODE = cte2.ZONE_CODE\n" +
//                ")\n" +
//                "SELECT ZONE_NAME, COMM_NAME, ZONE_CODE, col9, col3, total_score,\n" +
//                "       DENSE_RANK() OVER (ORDER BY total_score DESC) AS z_rank\n" +
//                "FROM ranked_data;\n";

		return queryGst14aa;
	}
	public String QueryFor_gst6a_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);

		String queryGst14aa= "WITH cte1 AS (\n" +
				"SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME, \n" +
				"(14c.COMM_DISPOSAL_NO + 14c.JC_DISPOSAL_NO + 14c.AC_DISPOSAL_NO + 14c.SUP_DISPOSAL_NO) AS col9 \n" +
				"FROM mis_gst_commcode AS cc RIGHT JOIN mis_dgi_st_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE \n" +
				"WHERE 14c.MM_YYYY = '" + month_date + "' AND zc.ZONE_NAME NOT IN ('DG East', 'CEI DG')\n" +
				"), \n" +
				"cte2 AS (\n" +
				"SELECT zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME,(14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col3 \n" +
				"FROM mis_gst_commcode AS cc RIGHT JOIN mis_dgi_st_1a AS 14c \n" +
				"ON cc.COMM_CODE = 14c.COMM_CODE LEFT JOIN mis_gst_zonecode AS zc \n" +
				"ON zc.ZONE_CODE = cc.ZONE_CODE \n" +
				"WHERE 14c.MM_YYYY = '" + prev_month_new + "' AND zc.ZONE_NAME NOT IN ('DG East', 'CEI DG')\n" +
				"), \n" +
				"final_data AS (\n" +
				"SELECT cte1.ZONE_NAME, cte1.ZONE_CODE, cte1.COMM_NAME, cte1.col9, cte2.col3, cte1.col9 AS numerator_6a, \n" +
				"CASE \n" +
				"WHEN cte2.col3 = 0 THEN 0 \n" +
				"ELSE (cte1.col9 / cte2.col3) * 100 \n" +
				"END AS total_score \n" +
				"FROM \n" +
				"cte1 \n" +
				"JOIN \n" +
				"cte2 ON \n" +
				"cte1.ZONE_NAME = cte2.ZONE_NAME \n" +
				"AND cte1.ZONE_CODE = cte2.ZONE_CODE \n" +
				"AND cte1.COMM_NAME = cte2.COMM_NAME\n" +
				"), \n" +
				"ranked_data AS (\n" +
				"SELECT final_data.*, \n" +
				"ROW_NUMBER() OVER (ORDER BY final_data.numerator_6a) AS row_num, \n" +
				"COUNT(*) OVER () AS total_rows\n" +
				"FROM final_data\n" +
				")\n" +
				"SELECT \n" +
				"ranked_data.ZONE_NAME, ranked_data.ZONE_CODE, ranked_data.COMM_NAME, ranked_data.col9, ranked_data.col3, ranked_data.total_score, ranked_data.numerator_6a, \n" +
				"CASE \n" +
				"WHEN total_rows % 2 = 1 THEN \n" +
				"(SELECT numerator_6a FROM ranked_data WHERE row_num = (total_rows + 1) / 2) \n" +
				"ELSE\n" +
				"(SELECT AVG(numerator_6a) FROM ranked_data WHERE row_num IN (total_rows / 2, (total_rows / 2) + 1)) \n" +
				"END AS median_numerator_6a, \n" +
				"DENSE_RANK() OVER (ORDER BY total_score DESC) AS z_rank \n" +
				"FROM ranked_data;\n";
//        String queryGst14aa= "WITH cte1 AS (\n" +
//                "    SELECT \n" +
//                "        zc.ZONE_NAME, \n" +
//                "        cc.ZONE_CODE, \n" +
//                "        cc.COMM_NAME, \n" +
//                "        (14c.COMM_DISPOSAL_NO + 14c.JC_DISPOSAL_NO + 14c.AC_DISPOSAL_NO + 14c.SUP_DISPOSAL_NO) AS col9 \n" +
//                "    FROM \n" +
//                "        mis_gst_commcode AS cc \n" +
//                "    RIGHT JOIN \n" +
//                "        mis_dgi_st_1a AS 14c \n" +
//                "    ON \n" +
//                "        cc.COMM_CODE = 14c.COMM_CODE \n" +
//                "    LEFT JOIN \n" +
//                "        mis_gst_zonecode AS zc \n" +
//                "    ON \n" +
//                "        zc.ZONE_CODE = cc.ZONE_CODE \n" +
//                "    WHERE \n" +
//                "        14c.MM_YYYY = '" + month_date + "'\n" +
//                "        AND zc.ZONE_NAME NOT IN ('DG East', 'CEI DG')\n" +
//                "),\n" +
//                "cte2 AS (\n" +
//                "    SELECT \n" +
//                "        zc.ZONE_NAME, \n" +
//                "        cc.ZONE_CODE, \n" +
//                "        cc.COMM_NAME, \n" +
//                "        (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col3 \n" +
//                "    FROM \n" +
//                "        mis_gst_commcode AS cc \n" +
//                "    RIGHT JOIN \n" +
//                "        mis_dgi_st_1a AS 14c \n" +
//                "    ON \n" +
//                "        cc.COMM_CODE = 14c.COMM_CODE \n" +
//                "    LEFT JOIN \n" +
//                "        mis_gst_zonecode AS zc \n" +
//                "    ON \n" +
//                "        zc.ZONE_CODE = cc.ZONE_CODE \n" +
//                "    WHERE \n" +
//                "        14c.MM_YYYY = '" + prev_month_new + "'\n" +
//                "        AND zc.ZONE_NAME NOT IN ('DG East', 'CEI DG')\n" +
//                "),\n" +
//                "final_data AS (\n" +
//                "    SELECT \n" +
//                "        cte1.ZONE_NAME, \n" +
//                "        cte1.ZONE_CODE, \n" +
//                "        cte1.COMM_NAME, \n" +
//                "        cte1.col9, \n" +
//                "        cte2.col3,\n" +
//                "        CASE \n" +
//                "            WHEN cte2.col3 = 0 THEN 0 -- Use 0 instead of NULL\n" +
//                "            ELSE (cte1.col9 / cte2.col3) * 100 \n" +
//                "        END AS total_score\n" +
//                "    FROM \n" +
//                "        cte1\n" +
//                "    JOIN \n" +
//                "        cte2 \n" +
//                "    ON \n" +
//                "        cte1.ZONE_NAME = cte2.ZONE_NAME \n" +
//                "        AND cte1.ZONE_CODE = cte2.ZONE_CODE \n" +
//                "        AND cte1.COMM_NAME = cte2.COMM_NAME\n" +
//                ")\n" +
//                "SELECT \n" +
//                "    ZONE_NAME, \n" +
//                "    ZONE_CODE, \n" +
//                "    COMM_NAME, \n" +
//                "    col9, \n" +
//                "    col3, \n" +
//                "    total_score,\n" +
//                "    DENSE_RANK() OVER (ORDER BY total_score DESC) AS z_rank\n" +
//                "FROM \n" +
//                "    final_data;\n";

		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst6b_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH RankedData AS (\n"
				+ "    SELECT zc.ZONE_NAME,cc.ZONE_CODE,\n"
				+ "        SUM(14c.COMM_MORE_YEAR_AMT +14c.JC_MORE_YEAR_AMT +14c.AC_MORE_YEAR_AMT +14c.SUP_MORE_YEAR_AMT) AS col18,SUM(14c.COMM_CLOSING_NO +14c.JC_CLOSING_NO +14c.AC_CLOSING_NO +14c.SUP_CLOSING_NO) AS col13,\n"
				+ "        CASE \n"
				+ "            WHEN SUM(\n"
				+ "                14c.COMM_CLOSING_NO +14c.JC_CLOSING_NO +14c.AC_CLOSING_NO +14c.SUP_CLOSING_NO\n"
				+ "            ) = 0 THEN 0\n"
				+ "            ELSE (SUM(14c.COMM_MORE_YEAR_AMT +14c.JC_MORE_YEAR_AMT +14c.AC_MORE_YEAR_AMT +14c.SUP_MORE_YEAR_AMT\n"
				+ "            ) / NULLIF(SUM(14c.COMM_CLOSING_NO +14c.JC_CLOSING_NO +14c.AC_CLOSING_NO +14c.SUP_CLOSING_NO), 0)) * 100\n"
				+ "        END AS total_score\n"
				+ "    FROM mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN mis_dgi_st_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE 14c.MM_YYYY = '" + month_date + "' GROUP BY cc.ZONE_CODE,zc.ZONE_NAME\n"
				+ "),\n"
				+ "RankedDataWithRank AS (\n"
				+ "    SELECT *,\n"
				+ "        RANK() OVER (ORDER BY total_score ASC) AS z_rank\n"
				+ "    FROM RankedData\n"
				+ ")\n"
				+ "SELECT ZONE_NAME,ZONE_CODE,col18,col13,total_score,z_rank\n"
				+ "FROM RankedDataWithRank;";
		return queryGst14aa;
	}
	public String QueryFor_gst6b_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.COMM_NAME, \n" +
				"    cc.ZONE_CODE,\n" +
				"    (14c.COMM_MORE_YEAR_AMT + 14c.JC_MORE_YEAR_AMT + 14c.AC_MORE_YEAR_AMT + 14c.SUP_MORE_YEAR_AMT) AS col18,\n" +
				"    (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col13,\n" +
				"    ((14c.COMM_MORE_YEAR_AMT + 14c.JC_MORE_YEAR_AMT + 14c.AC_MORE_YEAR_AMT + 14c.SUP_MORE_YEAR_AMT) / \n" +
				"     (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO)) * 100 AS total_score \n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN \n" +
				"    mis_dgi_st_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE \n" +
				"    14c.MM_YYYY = '" + month_date + "' AND \n" +
				"    zc.ZONE_CODE = '"+zone_code+"'\n" +
				"ORDER BY \n" +
				"    total_score ASC;\n";

		return queryGst14aa;
	}
	public String QueryFor_gst6b_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.COMM_NAME, \n" +
				"    cc.ZONE_CODE,\n" +
				"    (14c.COMM_MORE_YEAR_AMT + 14c.JC_MORE_YEAR_AMT + 14c.AC_MORE_YEAR_AMT + 14c.SUP_MORE_YEAR_AMT) AS col18,\n" +
				"    (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col13,\n" +
				"    CASE \n" +
				"        WHEN (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) = 0 THEN 0\n" +
				"        ELSE ((14c.COMM_MORE_YEAR_AMT + 14c.JC_MORE_YEAR_AMT + 14c.AC_MORE_YEAR_AMT + 14c.SUP_MORE_YEAR_AMT) / \n" +
				"              (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO)) * 100\n" +
				"    END AS total_score\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc \n" +
				"    RIGHT JOIN mis_dgi_st_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE \n" +
				"WHERE \n" +
				"    14c.MM_YYYY =  '" + month_date + "'\n" +
				"ORDER BY \n" +
				"    total_score ASC;\n";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst6c_ZoneWise(String month_date){
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH disposal_data AS (\n" +
				"                    SELECT zc.ZONE_NAME, cc.ZONE_CODE,\n" +
				"                        SUM(COALESCE(14c.COMM_DISPOSAL_NO, 0) +COALESCE(14c.JC_DISPOSAL_NO, 0) +COALESCE(14c.AC_DISPOSAL_NO, 0) +COALESCE(14c.SUP_DISPOSAL_NO, 0)\n" +
				"                        ) AS numerator_6c,\n" +
				"                        ROW_NUMBER() OVER (ORDER BY SUM(COALESCE(14c.COMM_DISPOSAL_NO, 0) +COALESCE(14c.JC_DISPOSAL_NO, 0) +COALESCE(14c.AC_DISPOSAL_NO, 0) +COALESCE(14c.SUP_DISPOSAL_NO, 0)\n" +
				"                        )) AS row_num,\n" +
				"                        COUNT(*) OVER () AS total_count\n" +
				"                    FROM mis_gst_commcode AS cc\n" +
				"                    RIGHT JOIN mis_dgi_ce_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n" +
				"                    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"                    WHERE 14c.MM_YYYY = '" + month_date + "'  -- Current month\\n\" +\n" +
				"                    GROUP BY zc.ZONE_NAME, cc.ZONE_CODE\n" +
				"                    ORDER BY numerator_6c\n" +
				"                ),\n" +
				"                median_data AS (\n" +
				"                    SELECT\n" +
				"                        CASE\n" +
				"                            WHEN total_count % 2 = 1 THEN\n" +
				"                                (SELECT numerator_6c FROM disposal_data WHERE row_num = (total_count + 1) / 2)\n" +
				"                            ELSE\n" +
				"                                (SELECT numerator_6c FROM disposal_data \n" +
				"                                 WHERE row_num = total_count / 2) -- Select the lower middle value in the case of even count\\n\" +\n" +
				"                        END AS median_numerator_6c\n" +
				"                    FROM disposal_data\n" +
				"                    LIMIT 1\n" +
				"                ),\n" +
				"                closing_data AS (\n" +
				"                    SELECT zc.ZONE_NAME, cc.ZONE_CODE,\n" +
				"                        SUM(COALESCE(14c.COMM_CLOSING_NO, 0) +COALESCE(14c.JC_CLOSING_NO, 0) +COALESCE(14c.AC_CLOSING_NO, 0) +COALESCE(14c.SUP_CLOSING_NO, 0)\n" +
				"\t        ) AS col3\n" +
				"\t    FROM mis_gst_commcode AS cc\n" +
				"                    RIGHT JOIN mis_dgi_ce_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n" +
				"                    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"                    WHERE 14c.MM_YYYY = '" + prev_month_new + "'  -- Previous month\n" +
				"                    GROUP BY zc.ZONE_NAME, cc.ZONE_CODE\n" +
				"                ),\n" +
				"                ranked_data AS (\n" +
				"                    SELECT d.ZONE_NAME, d.ZONE_CODE,d.numerator_6c,\n" +
				"                        COALESCE(c.col3, 0) AS col3,  -- Handle possible NULL values\n" +
				"                        CASE WHEN COALESCE(c.col3, 0) = 0 THEN 0\n" +
				"                            ELSE (d.numerator_6c * 100 / COALESCE(c.col3, 0)) \n" +
				"                        END AS score_of_parameter6c,\n" +
				"                        CONCAT(d.numerator_6c, '/', COALESCE(c.col3, 0)) AS absval  -- Format absolute values\n" +
				"                    FROM disposal_data AS d\n" +
				"                    LEFT JOIN closing_data AS c ON d.ZONE_NAME = c.ZONE_NAME AND d.ZONE_CODE = c.ZONE_CODE\n" +
				"                )\n" +
				"                SELECT t.ZONE_NAME, t.ZONE_CODE,t.numerator_6c,t.col3,t.score_of_parameter6c,t.absval,m.median_numerator_6c\n" +
				"                FROM ranked_data AS t\n" +
				"                CROSS JOIN median_data AS m\n" +
				"                WHERE t.ZONE_NAME NOT IN ('DG East', 'CEI DG');";
		return queryGst14aa;
	}
	public String QueryFor_gst6c_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH CTE1 AS (\n" +
				"SELECT zc.ZONE_NAME, cc.COMM_NAME, cc.ZONE_CODE,\n" +
				"(14c.COMM_DISPOSAL_NO + 14c.JC_DISPOSAL_NO + 14c.AC_DISPOSAL_NO + 14c.SUP_DISPOSAL_NO) AS col9\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_dgi_ce_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 14c.MM_YYYY = '" + month_date + "'\n" +
				"),\n" +
				"CTE2 AS (\n" +
				"SELECT zc.ZONE_NAME, cc.COMM_NAME, cc.ZONE_CODE,\n" +
				"(14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col3\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_dgi_ce_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 14c.MM_YYYY = '" + prev_month_new + "'\n" +
				"),\n" +
				"CTE_Median AS (\n" +
				"SELECT ZONE_NAME, COMM_NAME, ZONE_CODE, col9,\n" +
				"(SELECT col9\n" +
				"FROM (SELECT col9, ROW_NUMBER() OVER (ORDER BY col9) AS row_num\n" +
				"FROM CTE1) AS ranked\n" +
				"WHERE row_num = FLOOR((SELECT COUNT(*) FROM CTE1) / 2) + 1\n" +
				") AS median_6c\n" +
				"FROM CTE1\n" +
				")\n" +
				"SELECT CTE1.ZONE_NAME, CTE1.COMM_NAME, CTE1.ZONE_CODE, CTE1.col9, CTE2.col3,\n" +
				"(CASE WHEN CTE2.col3 = 0 THEN 0 ELSE (CTE1.col9 / CTE2.col3) * 100 END) AS total_score,\n" +
				"CTE_Median.median_6c\n" +
				"FROM CTE1\n" +
				"JOIN CTE2 \n" +
				"ON CTE1.ZONE_NAME = CTE2.ZONE_NAME \n" +
				"AND CTE1.COMM_NAME = CTE2.COMM_NAME \n" +
				"AND CTE1.ZONE_CODE = CTE2.ZONE_CODE\n" +
				"JOIN CTE_Median \n" +
				"ON CTE1.ZONE_NAME = CTE_Median.ZONE_NAME \n" +
				"AND CTE1.COMM_NAME = CTE_Median.COMM_NAME \n" +
				"AND CTE1.ZONE_CODE = CTE_Median.ZONE_CODE\n" +
				"WHERE CTE1.ZONE_CODE ='" + zone_code + "'\n" +
				"ORDER BY total_score DESC;";
		return queryGst14aa;
	}
	public String QueryFor_gst6c_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH CTE1 AS (\n" +
				"SELECT zc.ZONE_NAME, cc.COMM_NAME, cc.ZONE_CODE,\n" +
				"(14c.COMM_DISPOSAL_NO + 14c.JC_DISPOSAL_NO + 14c.AC_DISPOSAL_NO + 14c.SUP_DISPOSAL_NO) AS col9\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_dgi_ce_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE WHERE 14c.MM_YYYY = '" + month_date + "'\n" +
				"),\n" +
				"CTE2 AS (\n" +
				"SELECT zc.ZONE_NAME, cc.COMM_NAME, cc.ZONE_CODE,\n" +
				"(14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col3\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_dgi_ce_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 14c.MM_YYYY = '" + prev_month_new + "'\n" +
				"),\n" +
				"CTE_Median AS (\n" +
				"SELECT ZONE_NAME, COMM_NAME, ZONE_CODE, col9,\n" +
				"(SELECT col9\n" +
				"FROM (SELECT col9, ROW_NUMBER() OVER (ORDER BY col9) AS row_num\n" +
				"FROM CTE1) AS ranked\n" +
				"WHERE row_num = FLOOR((SELECT COUNT(*) FROM CTE1) / 2) + 1) AS median_6c\n" +
				"FROM CTE1)\n" +
				"SELECT CTE1.ZONE_NAME, CTE1.COMM_NAME, CTE1.ZONE_CODE, CTE1.col9, CTE2.col3,\n" +
				"(CASE WHEN CTE2.col3 = 0 THEN 0 ELSE (CTE1.col9 / CTE2.col3) * 100 END) AS total_score,\n" +
				"CTE_Median.median_6c\n" +
				"FROM CTE1\n" +
				"JOIN CTE2 \n" +
				"ON CTE1.ZONE_NAME = CTE2.ZONE_NAME \n" +
				"AND CTE1.COMM_NAME = CTE2.COMM_NAME \n" +
				"AND CTE1.ZONE_CODE = CTE2.ZONE_CODE\n" +
				"JOIN CTE_Median \n" +
				"ON CTE1.ZONE_NAME = CTE_Median.ZONE_NAME \n" +
				"AND CTE1.COMM_NAME = CTE_Median.COMM_NAME \n" +
				"AND CTE1.ZONE_CODE = CTE_Median.ZONE_CODE\n" +
				"ORDER BY total_score DESC;";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst6d_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n" +
				"    zc.ZONE_NAME,\n" +
				"    cc.ZONE_CODE,\n" +
				"    SUM(\n" +
				"        14c.COMM_MORE_YEAR_AMT +\n" +
				"        14c.JC_MORE_YEAR_AMT +\n" +
				"        14c.AC_MORE_YEAR_AMT +\n" +
				"        14c.SUP_MORE_YEAR_AMT\n" +
				"    ) AS col18,\n" +
				"    SUM(\n" +
				"        14c.COMM_CLOSING_NO +\n" +
				"        14c.JC_CLOSING_NO +\n" +
				"        14c.AC_CLOSING_NO +\n" +
				"        14c.SUP_CLOSING_NO\n" +
				"    ) AS col13,\n" +
				"    COALESCE(\n" +
				"        (SUM(\n" +
				"            14c.COMM_MORE_YEAR_AMT +\n" +
				"            14c.JC_MORE_YEAR_AMT +\n" +
				"            14c.AC_MORE_YEAR_AMT +\n" +
				"            14c.SUP_MORE_YEAR_AMT\n" +
				"        ) / NULLIF(SUM(\n" +
				"            14c.COMM_CLOSING_NO +\n" +
				"            14c.JC_CLOSING_NO +\n" +
				"            14c.AC_CLOSING_NO +\n" +
				"            14c.SUP_CLOSING_NO\n" +
				"        ), 0)) * 100, 0\n" +
				"    ) AS total_score\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_dgi_ce_1a AS 14c\n" +
				"    ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc\n" +
				"    ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 14c.MM_YYYY = '" + month_date + "'\n" +
				"GROUP BY cc.ZONE_CODE, zc.ZONE_NAME\n" +
				"ORDER BY total_score ASC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst6d_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT zc.ZONE_NAME,\n" +
				"       cc.COMM_NAME,\n" +
				"       cc.ZONE_CODE,\n" +
				"       (14c.COMM_MORE_YEAR_AMT + 14c.JC_MORE_YEAR_AMT + 14c.AC_MORE_YEAR_AMT + 14c.SUP_MORE_YEAR_AMT) AS col18,\n" +
				"       (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) AS col13,\n" +
				"       ((14c.COMM_MORE_YEAR_AMT + 14c.JC_MORE_YEAR_AMT + 14c.AC_MORE_YEAR_AMT + 14c.SUP_MORE_YEAR_AMT) /\n" +
				"       (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO) * 100) AS total_score\n" +
				"FROM mis_gst_commcode AS cc\n" +
				"RIGHT JOIN mis_dgi_ce_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE 14c.MM_YYYY = '" + month_date + "' AND zc.ZONE_CODE = '"+zone_code+"'\n" +
				"ORDER BY total_score ASC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst6d_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n" +
				"    zc.ZONE_NAME, \n" +
				"    cc.ZONE_CODE, \n" +
				"    cc.COMM_NAME,\n" +
				"    IFNULL((14c.COMM_MORE_YEAR_AMT + 14c.JC_MORE_YEAR_AMT + 14c.AC_MORE_YEAR_AMT + 14c.SUP_MORE_YEAR_AMT), 0) AS col18, \n" +
				"    IFNULL((14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO), 0) AS col13,\n" +
				"    IFNULL((14c.COMM_MORE_YEAR_AMT + 14c.JC_MORE_YEAR_AMT + 14c.AC_MORE_YEAR_AMT + 14c.SUP_MORE_YEAR_AMT) / \n" +
				"    (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO), 0) AS total_score_of_subpara4,\n" +
				"    IFNULL(((14c.COMM_MORE_YEAR_AMT + 14c.JC_MORE_YEAR_AMT + 14c.AC_MORE_YEAR_AMT + 14c.SUP_MORE_YEAR_AMT) / \n" +
				"    (14c.COMM_CLOSING_NO + 14c.JC_CLOSING_NO + 14c.AC_CLOSING_NO + 14c.SUP_CLOSING_NO)) * 100, 0) AS total_score\n" +
				"FROM \n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN \n" +
				"    mis_dgi_ce_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n" +
				"LEFT JOIN \n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE \n" +
				"    14c.MM_YYYY = '" + month_date + "'\n" +
				"ORDER BY \n" +
				"    total_score ASC;\n";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst7_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT " +
				"SUM(14c.opening_balance_no + 14c.RFD_01_NO - 14c.RFD_03_NO - 14c.RFD_06_SANCTIONED_NO - 14c.RFD_06_REJECTED_NO) AS col16, " +
				"SUM(14c.age_breakup_above60_no) AS col22, " +
				"cc.ZONE_CODE,zc.ZONE_NAME " +
				"FROM mis_gst_commcode AS cc " +
				"RIGHT JOIN mis_dpm_gst_4 AS 14c " +
				"ON cc.COMM_CODE = 14c.COMM_CODE " +
				"LEFT JOIN mis_gst_zonecode AS zc " +
				"ON zc.ZONE_CODE = cc.ZONE_CODE " +
				"WHERE 14c.MM_YYYY = '" + month_date + "' " +
				"GROUP BY cc.ZONE_CODE;";
		return queryGst14aa;
	}
	public String QueryFor_gst7_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT \r\n"
				+ "    cc.ZONE_CODE,\r\n"
				+ "    zc.ZONE_NAME,\r\n"
				+ "    cc.COMM_NAME,\r\n"
				+ "    SUM(dpm.opening_balance_no + dpm.RFD_01_NO - dpm.RFD_03_NO - dpm.RFD_06_SANCTIONED_NO - dpm.RFD_06_REJECTED_NO) AS col16,\r\n"
				+ "    SUM(dpm.age_breakup_above60_no) AS col22\r\n"
				+ "FROM mis_gst_commcode AS cc\r\n"
				+ "RIGHT JOIN mis_dpm_gst_4 AS dpm\r\n"
				+ "    ON cc.COMM_CODE = dpm.COMM_CODE\r\n"
				+ "LEFT JOIN mis_gst_zonecode AS zc\r\n"
				+ "    ON zc.ZONE_CODE = cc.ZONE_CODE\r\n"
				+ "WHERE dpm.MM_YYYY = '" + month_date + "' AND zc.ZONE_CODE = '"+zone_code+"'\r\n"
				+ "GROUP BY \r\n"
				+ "    cc.ZONE_CODE, \r\n"
				+ "    zc.ZONE_NAME,\r\n"
				+ "    cc.COMM_NAME;\r\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst7_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \r\n"
				+ "    cc.ZONE_CODE,\r\n"
				+ "    zc.ZONE_NAME,\r\n"
				+ "    cc.COMM_NAME,\r\n"
				+ "    SUM(dpm.opening_balance_no + dpm.RFD_01_NO - dpm.RFD_03_NO - dpm.RFD_06_SANCTIONED_NO - dpm.RFD_06_REJECTED_NO) AS col16,\r\n"
				+ "    SUM(dpm.age_breakup_above60_no) AS col22\r\n"
				+ "FROM mis_gst_commcode AS cc\r\n"
				+ "RIGHT JOIN mis_dpm_gst_4 AS dpm\r\n"
				+ "    ON cc.COMM_CODE = dpm.COMM_CODE\r\n"
				+ "LEFT JOIN mis_gst_zonecode AS zc\r\n"
				+ "    ON zc.ZONE_CODE = cc.ZONE_CODE\r\n"
				+ "WHERE dpm.MM_YYYY = '" + month_date + "' \r\n"
				+ "GROUP BY \r\n"
				+ "    cc.ZONE_CODE, \r\n"
				+ "    zc.ZONE_NAME,\r\n"
				+ "    cc.COMM_NAME;\r\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst8a_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa="WITH Summary AS (\n"
				+ "    SELECT\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        IFNULL(SUM(tc.ARREAR_REALISED_AMT), 0) AS col15,\n"
				+ "        IFNULL(SUM(tc.TARGET_UPTO), 0) AS col3,\n"
				+ "        -- Calculate total_score using the formula, replace NULL with 0\n"
				+ "        IFNULL(SUM(tc.ARREAR_REALISED_AMT) / NULLIF(SUM(tc.TARGET_UPTO), 0), 0) * 100 AS total_score8A\n"
				+ "    FROM\n"
				+ "        mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN\n"
				+ "        mis_tar_gst_3_new AS tc ON cc.COMM_CODE = tc.COMM_CODE\n"
				+ "    LEFT JOIN\n"
				+ "        mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE\n"
				+ "        tc.MM_YYYY ='" + month_date + "' -- Replace with actual date values\n"
				+ "    GROUP BY\n"
				+ "        cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "MedianCalc AS (\n"
				+ "    SELECT\n"
				+ "        *,\n"
				+ "        -- Calculate median using a rank-based approach\n"
				+ "        (\n"
				+ "            SELECT IFNULL(AVG(col15), 0)\n"
				+ "            FROM (\n"
				+ "                SELECT \n"
				+ "                    col15, \n"
				+ "                    ROW_NUMBER() OVER (ORDER BY col15) AS rn\n"
				+ "                FROM Summary\n"
				+ "            ) ranked_summary\n"
				+ "            WHERE ranked_summary.rn IN (\n"
				+ "                (SELECT FLOOR((COUNT(*) + 1) / 2) FROM Summary),\n"
				+ "                (SELECT CEIL((COUNT(*) + 1) / 2) FROM Summary)\n"
				+ "            )\n"
				+ "        ) AS median_col15\n"
				+ "    FROM\n"
				+ "        Summary\n"
				+ ")\n"
				+ "SELECT\n"
				+ "    ZONE_CODE,\n"
				+ "    ZONE_NAME,\n"
				+ "    col15,\n"
				+ "    col3,\n"
				+ "    total_score8A,\n"
				+ "    IFNULL(median_col15, 0) AS median_col15,\n"
				+ "    -- Add absval as p/q form, replace NULL with 0\n"
				+ "    CONCAT(IFNULL(col15, 0), '/', IFNULL(col3, 0)) AS absval\n"
				+ "FROM\n"
				+ "    MedianCalc\n"
				+ "ORDER BY\n"
				+ "    total_score8A DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst8a_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa= "WITH Summary AS (\n"
				+ "    SELECT\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        IFNULL(SUM(tc.ARREAR_REALISED_AMT), 0) AS col15,\n"
				+ "        IFNULL(SUM(tc.TARGET_UPTO), 0) AS col3,\n"
				+ "        -- Calculate total_score using the formula, replace NULL with 0\n"
				+ "        IFNULL(SUM(tc.ARREAR_REALISED_AMT) / NULLIF(SUM(tc.TARGET_UPTO), 0), 0) * 100 AS total_score8A\n"
				+ "    FROM\n"
				+ "        mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN\n"
				+ "        mis_tar_gst_3_new AS tc ON cc.COMM_CODE = tc.COMM_CODE\n"
				+ "    LEFT JOIN\n"
				+ "        mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE\n"
				+ "        tc.MM_YYYY = '" + month_date + "' -- Replace with actual date values\n"
				+ "    GROUP BY\n"
				+ "        cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Include COMM_NAME in GROUP BY\n"
				+ "),\n"
				+ "MedianCalc AS (\n"
				+ "    SELECT\n"
				+ "        *,\n"
				+ "        -- Calculate median using a rank-based approach\n"
				+ "        (\n"
				+ "            SELECT IFNULL(AVG(col15), 0)\n"
				+ "            FROM (\n"
				+ "                SELECT \n"
				+ "                    col15, \n"
				+ "                    ROW_NUMBER() OVER (ORDER BY col15) AS rn\n"
				+ "                FROM Summary\n"
				+ "            ) ranked_summary\n"
				+ "            WHERE ranked_summary.rn IN (\n"
				+ "                (SELECT FLOOR((COUNT(*) + 1) / 2) FROM Summary),\n"
				+ "                (SELECT CEIL((COUNT(*) + 1) / 2) FROM Summary)\n"
				+ "            )\n"
				+ "        ) AS median_col15\n"
				+ "    FROM\n"
				+ "        Summary\n"
				+ ")\n"
				+ "SELECT\n"
				+ "    ZONE_CODE,\n"
				+ "    ZONE_NAME,\n"
				+ "    COMM_NAME, -- Include COMM_NAME in final SELECT\n"
				+ "    col15,\n"
				+ "    col3,\n"
				+ "    total_score8A,\n"
				+ "    IFNULL(median_col15, 0) AS median_col15,\n"
				+ "    -- Add absval as p/q form, replace NULL with 0\n"
				+ "    CONCAT(IFNULL(col15, 0), '/', IFNULL(col3, 0)) AS absval\n"
				+ "FROM\n"
				+ "    MedianCalc\n"
				+ "WHERE\n"
				+ "    ZONE_CODE = '" + zone_code + "' -- Added condition to filter by ZONE_CODE\n"
				+ "ORDER BY\n"
				+ "    total_score8A DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst8a_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa= "WITH Summary AS (\n"
				+ "    SELECT\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        IFNULL(SUM(tc.ARREAR_REALISED_AMT), 0) AS col15,\n"
				+ "        IFNULL(SUM(tc.TARGET_UPTO), 0) AS col3,\n"
				+ "        -- Calculate total_score using the formula, replace NULL with 0\n"
				+ "        IFNULL(SUM(tc.ARREAR_REALISED_AMT) / NULLIF(SUM(tc.TARGET_UPTO), 0), 0) * 100 AS total_score8A\n"
				+ "    FROM\n"
				+ "        mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN\n"
				+ "        mis_tar_gst_3_new AS tc ON cc.COMM_CODE = tc.COMM_CODE\n"
				+ "    LEFT JOIN\n"
				+ "        mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE\n"
				+ "        tc.MM_YYYY = '" + month_date + "' -- Replace with actual date values\n"
				+ "    GROUP BY\n"
				+ "        cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Include COMM_NAME in GROUP BY\n"
				+ "),\n"
				+ "MedianCalc AS (\n"
				+ "    SELECT\n"
				+ "        *,\n"
				+ "        -- Calculate median using a rank-based approach\n"
				+ "        (\n"
				+ "            SELECT IFNULL(AVG(col15), 0)\n"
				+ "            FROM (\n"
				+ "                SELECT \n"
				+ "                    col15, \n"
				+ "                    ROW_NUMBER() OVER (ORDER BY col15) AS rn\n"
				+ "                FROM Summary\n"
				+ "            ) ranked_summary\n"
				+ "            WHERE ranked_summary.rn IN (\n"
				+ "                (SELECT FLOOR((COUNT(*) + 1) / 2) FROM Summary),\n"
				+ "                (SELECT CEIL((COUNT(*) + 1) / 2) FROM Summary)\n"
				+ "            )\n"
				+ "        ) AS median_col15\n"
				+ "    FROM\n"
				+ "        Summary\n"
				+ ")\n"
				+ "SELECT\n"
				+ "    ZONE_CODE,\n"
				+ "    ZONE_NAME,\n"
				+ "    COMM_NAME, -- Include COMM_NAME in final SELECT\n"
				+ "    col15,\n"
				+ "    col3,\n"
				+ "    total_score8A,\n"
				+ "    IFNULL(median_col15, 0) AS median_col15,\n"
				+ "    -- Add absval as p/q form, replace NULL with 0\n"
				+ "    CONCAT(IFNULL(col15, 0), '/', IFNULL(col3, 0)) AS absval\n"
				+ "FROM\n"
				+ "    MedianCalc\n"
				+ "ORDER BY\n"
				+ "    total_score8A DESC;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst8b_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT\n"
				+ "    cc.ZONE_CODE,\n"
				+ "    zc.ZONE_NAME,\n"
				+ "    SUM(tc.CLOSING_AMT) AS col21,\n"
				+ "    SUM(tc.BELOW_YEAR_AMT) AS col23,\n"
				+ "    -- Calculate total_score using the formula\n"
				+ "    (SUM(tc.CLOSING_AMT) - SUM(tc.BELOW_YEAR_AMT)) / NULLIF(SUM(tc.CLOSING_AMT), 0) * 100 AS total_score,\n"
				+ "    -- Calculate absolute value in p/q form\n"
				+ "    CONCAT(\n"
				+ "        (SUM(tc.CLOSING_AMT) - SUM(tc.BELOW_YEAR_AMT)), \n"
				+ "        '/', \n"
				+ "        NULLIF(SUM(tc.CLOSING_AMT), 0)\n"
				+ "    ) AS abs_value\n"
				+ "FROM\n"
				+ "    mis_gst_commcode AS cc\n"
				+ "RIGHT JOIN\n"
				+ "    mis_tar_gst_3_new AS tc ON cc.COMM_CODE = tc.COMM_CODE\n"
				+ "LEFT JOIN\n"
				+ "    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "WHERE\n"
				+ "    tc.MM_YYYY = '"+month_date+"' --\n"
				+ "GROUP BY\n"
				+ "    cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "ORDER BY\n"
				+ "    total_score ASC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst8b_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n"
				+ "    cc.ZONE_CODE, \n"
				+ "    zc.ZONE_NAME, \n"
				+ "    cc.COMM_NAME,  \n"
				+ "    MAX(14c.CLOSING_AMT) AS col21, \n"
				+ "    MAX(14c.BELOW_YEAR_AMT) AS col23,\n"
				+ "    ((MAX(14c.CLOSING_AMT) - MAX(14c.BELOW_YEAR_AMT)) / MAX(14c.CLOSING_AMT)) * 100 AS total_score,\n"
				+ "    -- Calculate absolute value in p/q form\n"
				+ "    CONCAT(\n"
				+ "        (MAX(14c.CLOSING_AMT) - MAX(14c.BELOW_YEAR_AMT)), \n"
				+ "        '/', \n"
				+ "        NULLIF(MAX(14c.CLOSING_AMT), 0)\n"
				+ "    ) AS abs_value\n"
				+ "FROM \n"
				+ "    mis_gst_commcode AS cc \n"
				+ "RIGHT JOIN \n"
				+ "    mis_tar_gst_3_new AS 14c -- Changed alias to t14c\n"
				+ "    ON cc.COMM_CODE = 14c.COMM_CODE \n"
				+ "LEFT JOIN \n"
				+ "    mis_gst_zonecode AS zc \n"
				+ "    ON zc.ZONE_CODE = cc.ZONE_CODE \n"
				+ "WHERE \n"
				+ "    14c.MM_YYYY = '"+month_date+"' \n"
				+ "    AND zc.ZONE_CODE = '" + zone_code + "'\n"
				+ "GROUP BY \n"
				+ "    cc.ZONE_CODE, \n"
				+ "    zc.ZONE_NAME, \n"
				+ "    cc.COMM_NAME\n"
				+ "ORDER BY \n"
				+ "    total_score ASC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst8b_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT \n"
				+ "    cc.ZONE_CODE, \n"
				+ "    zc.ZONE_NAME,\n"
				+ "    cc.COMM_NAME,  \n"
				+ "    MAX(14c.CLOSING_AMT) AS col21, \n"
				+ "    MAX(14c.BELOW_YEAR_AMT) AS col23,\n"
				+ "    ((MAX(14c.CLOSING_AMT) - MAX(14c.BELOW_YEAR_AMT)) / MAX(14c.CLOSING_AMT)) * 100 AS total_score,\n"
				+ "    -- Calculate absolute value in p/q form\n"
				+ "    CONCAT(\n"
				+ "        (MAX(14c.CLOSING_AMT) - MAX(14c.BELOW_YEAR_AMT)), \n"
				+ "        '/', \n"
				+ "        NULLIF(MAX(14c.CLOSING_AMT), 0)\n"
				+ "    ) AS abs_value\n"
				+ "FROM \n"
				+ "    mis_gst_commcode AS cc \n"
				+ "RIGHT JOIN \n"
				+ "    mis_tar_gst_3_new AS 14c ON cc.COMM_CODE = 14c.COMM_CODE \n"
				+ "LEFT JOIN \n"
				+ "    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE \n"
				+ "WHERE \n"
				+ "    14c.MM_YYYY = '"+month_date+"' \n"
				+ "GROUP BY \n"
				+ "    cc.ZONE_CODE, \n"
				+ "    zc.ZONE_NAME,\n"
				+ "    cc.COMM_NAME\n"
				+ "ORDER BY \n"
				+ "    total_score ASC;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst9a_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa="SELECT\n" +
				"    cc.ZONE_CODE,\n" +
				"    zc.ZONE_NAME,\n" +
				"    -- Calculate col15 with a range for up to the month (e.g., for financial year range)\n" +
				"    SUM(tc.PROSECUTION_NOT_LAUNCHED) AS col8,\n" +
				"    SUM(tc.PROSECUTION_SANCTIONED) AS col5,\n" +
				"    -- Calculate total_score using the formula\n" +
				"    (SUM(tc.PROSECUTION_NOT_LAUNCHED)) / NULLIF(SUM(tc.PROSECUTION_SANCTIONED), 0) * 100 AS total_score9A\n" +
				"FROM\n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN\n" +
				"    mis_dla_gst_lgl_4 AS tc ON cc.COMM_CODE = tc.COMM_CODE\n" +
				"LEFT JOIN\n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE\n" +
				"    tc.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "' -- Adjust this to your desired range for financial year\n" +
				"GROUP BY\n" +
				"    cc.ZONE_CODE, zc.ZONE_NAME\n" +
				"ORDER BY\n" +
				"    total_score9A asc;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst9a_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa="SELECT\n" +
				"    cc.ZONE_CODE,\n" +
				"    zc.ZONE_NAME,\n" +
				"    cc.COMM_NAME, -- Include COMM_NAME\n" +
				"    -- Calculate col15 with a range for up to the month (e.g., for financial year range)\n" +
				"    SUM(tc.PROSECUTION_NOT_LAUNCHED) AS col8,\n" +
				"    SUM(tc.PROSECUTION_SANCTIONED) AS col5,\n" +
				"    -- Calculate total_score using the formula\n" +
				"    (SUM(tc.PROSECUTION_NOT_LAUNCHED)) / NULLIF(SUM(tc.PROSECUTION_SANCTIONED), 0) * 100 AS total_score9A\n" +
				"FROM\n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN\n" +
				"    mis_dla_gst_lgl_4 AS tc ON cc.COMM_CODE = tc.COMM_CODE\n" +
				"LEFT JOIN\n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE\n" +
				"    zc.ZONE_CODE = 65-- Add condition for ZONE_CODE = '" + zone_code + "'\n" +
				"    AND tc.MM_YYYY BETWEEN '" + start_date + "' AND '"+month_date+"'  -- Adjust this to your desired range for financial year\n" +
				"GROUP BY\n" +
				"    cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Add COMM_NAME in GROUP BY clause\n" +
				"ORDER BY\n" +
				"    total_score9A ASC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst9a_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa="SELECT\n" +
				"    cc.ZONE_CODE,\n" +
				"    zc.ZONE_NAME,\n" +
				"    cc.COMM_NAME, -- Include COMM_NAME\n" +
				"    -- Calculate col15 with a range for up to the month (e.g., for financial year range)\n" +
				"    SUM(tc.PROSECUTION_NOT_LAUNCHED) AS col8,\n" +
				"    SUM(tc.PROSECUTION_SANCTIONED) AS col5,\n" +
				"    -- Calculate total_score using the formula\n" +
				"    (SUM(tc.PROSECUTION_NOT_LAUNCHED)) / NULLIF(SUM(tc.PROSECUTION_SANCTIONED), 0) * 100 AS total_score9A\n" +
				"FROM\n" +
				"    mis_gst_commcode AS cc\n" +
				"RIGHT JOIN\n" +
				"    mis_dla_gst_lgl_4 AS tc ON cc.COMM_CODE = tc.COMM_CODE\n" +
				"LEFT JOIN\n" +
				"    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"WHERE\n" +
				"    tc.MM_YYYY BETWEEN '" + start_date + "' AND '"+month_date+"'  -- Adjust this to your desired range for financial year\n" +
				"GROUP BY\n" +
				"    cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Add COMM_NAME in GROUP BY clause\n" +
				"ORDER BY\n" +
				"    total_score9A ASC;\n";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst9b_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa=  "WITH score_data AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME,\n"
				+ "        cc.ZONE_CODE,\n"
				+ "        SUM(14c.PROSECUTION_LAUNCHED) AS col4_4,\n"
				+ "        SUM(14c.ARRESTS_MADE) AS col1_4,\n"
				+ "        (SUM(14c.PROSECUTION_LAUNCHED) * 100 / SUM(14c.ARRESTS_MADE)) AS score_of_subparameter9b\n"
				+ "    FROM \n"
				+ "        mis_gst_commcode AS cc\n"
				+ "    RIGHT JOIN \n"
				+ "        mis_gi_gst_1a AS 14c ON cc.COMM_CODE = 14c.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        14c.MM_YYYY = '" + month_date + "'\n"
				+ "    GROUP BY \n"
				+ "        cc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "ranked_data AS (\n"
				+ "    SELECT \n"
				+ "        col4_4,\n"
				+ "        @row_number := @row_number + 1 AS row_num,\n"
				+ "        @total_rows := @total_rows + 1\n"
				+ "    FROM \n"
				+ "        score_data, (SELECT @row_number := 0, @total_rows := 0) AS vars\n"
				+ "    ORDER BY \n"
				+ "        col4_4\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    ZONE_NAME,\n"
				+ "    ZONE_CODE,\n"
				+ "    col4_4,\n"
				+ "    col1_4,\n"
				+ "    score_of_subparameter9b,\n"
				+ "    (SELECT \n"
				+ "        AVG(col4_4) \n"
				+ "     FROM \n"
				+ "        ranked_data \n"
				+ "     WHERE \n"
				+ "        row_num IN (FLOOR((@total_rows + 1) / 2), CEIL((@total_rows + 1) / 2))) AS median9_b\n"
				+ "FROM \n"
				+ "    score_data\n"
				+ "ORDER BY \n"
				+ "    score_of_subparameter9b DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst9b_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "SELECT \n"
				+ "    zc.ZONE_NAME,\n"
				+ "    cc.ZONE_CODE,\n"
				+ "    cc.COMM_NAME,\n"
				+ "    SUM(gst.PROSECUTION_LAUNCHED) AS col4_4,\n"
				+ "    SUM(gst.ARRESTS_MADE) AS col1_4,\n"
				+ "    (SUM(gst.PROSECUTION_LAUNCHED) * 100 / SUM(gst.ARRESTS_MADE)) AS score_of_subparameter9b,\n"
				+ "    (SELECT AVG(sub.prosecution_launched) AS median_9b\n"
				+ "     FROM (\n"
				+ "         SELECT SUM(gst.PROSECUTION_LAUNCHED) AS prosecution_launched\n"
				+ "         FROM mis_gst_commcode AS cc\n"
				+ "         RIGHT JOIN mis_gi_gst_1a AS gst ON cc.COMM_CODE = gst.COMM_CODE\n"
				+ "         LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "         WHERE gst.MM_YYYY = '" + month_date + "'\n"
				+ "         GROUP BY cc.COMM_CODE\n"
				+ "         ORDER BY prosecution_launched\n"
				+ "         LIMIT 2) AS sub) AS median_9b\n"
				+ "FROM \n"
				+ "    mis_gst_commcode AS cc\n"
				+ "RIGHT JOIN \n"
				+ "    mis_gi_gst_1a AS gst ON cc.COMM_CODE = gst.COMM_CODE\n"
				+ "LEFT JOIN \n"
				+ "    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "WHERE \n"
				+ "    gst.MM_YYYY = '" + month_date + "' \n"
				+ "    AND cc.ZONE_CODE = '" + zone_code + "'\n"
				+ "GROUP BY \n"
				+ "    zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME \n"
				+ "ORDER BY \n"
				+ "    score_of_subparameter9b DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst9b_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="SELECT \n"
				+ "    zc.ZONE_NAME,\n"
				+ "    cc.ZONE_CODE,\n"
				+ "    cc.COMM_NAME,\n"
				+ "    SUM(gst.PROSECUTION_LAUNCHED) AS col4_4,\n"
				+ "    SUM(gst.ARRESTS_MADE) AS col1_4,\n"
				+ "    (SUM(gst.PROSECUTION_LAUNCHED) * 100 / SUM(gst.ARRESTS_MADE)) AS score_of_subparameter9b,\n"
				+ "    (SELECT AVG(sub.prosecution_launched) AS median_9b\n"
				+ "     FROM (\n"
				+ "         SELECT SUM(gst.PROSECUTION_LAUNCHED) AS prosecution_launched\n"
				+ "         FROM mis_gst_commcode AS cc\n"
				+ "         RIGHT JOIN mis_gi_gst_1a AS gst ON cc.COMM_CODE = gst.COMM_CODE\n"
				+ "         LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "         WHERE gst.MM_YYYY = '" + month_date + "'\n"
				+ "         GROUP BY cc.COMM_CODE\n"
				+ "         ORDER BY prosecution_launched\n"
				+ "         LIMIT 2) AS sub) AS median_9b\n"
				+ "FROM \n"
				+ "    mis_gst_commcode AS cc\n"
				+ "RIGHT JOIN \n"
				+ "    mis_gi_gst_1a AS gst ON cc.COMM_CODE = gst.COMM_CODE\n"
				+ "LEFT JOIN \n"
				+ "    mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n"
				+ "WHERE \n"
				+ "    gst.MM_YYYY = '" + month_date + "' \n"
				+ "GROUP BY \n"
				+ "    zc.ZONE_NAME, cc.ZONE_CODE, cc.COMM_NAME \n"
				+ "ORDER BY \n"
				+ "    score_of_subparameter9b DESC;\n"
				+ "";
		return queryGst14aa;
	}
	// **********************************************GST_10a_ query*****************************************
	public String QueryFor_gst10a_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        SUM(UNIT_ALLOTTED_NO_LARGE + UNIT_ALLOTTED_NO_MEDIUM + UNIT_ALLOTTED_NO_SMALL) AS Col2, \n"
				+ "        SUM(UNIT_AUDITED_NO_LARGE + UNIT_AUDITED_NO_MEDIUM + UNIT_AUDITED_NO_SMALL) AS Col6\n"
				+ "    FROM \n"
				+ "        mis_dga_gst_adt_1 AS lgl\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        lgl.MM_YYYY = '" + month_date + "'\n"
				+ "    GROUP BY \n"
				+ "        zc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        COALESCE(SUM(UNIT_ALLOTTED_NO_LARGE + UNIT_ALLOTTED_NO_MEDIUM + UNIT_ALLOTTED_NO_SMALL), 0) AS Col3, \n"
				+ "        COALESCE(SUM(UNIT_AUDITED_NO_LARGE + UNIT_AUDITED_NO_MEDIUM + UNIT_AUDITED_NO_SMALL), 0) AS Col7\n"
				+ "    FROM \n"
				+ "        mis_dga_gst_adt_1 AS lgl\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        lgl.MM_YYYY <= '"+ prev_month_new+"'\n"
				+ "    GROUP BY \n"
				+ "        zc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "median_cte AS (\n"
				+ "    SELECT \n"
				+ "        AVG(Col6) AS median_col6\n"
				+ "    FROM (\n"
				+ "        SELECT \n"
				+ "            Col6,\n"
				+ "            ROW_NUMBER() OVER (ORDER BY Col6 ASC) AS row_num,\n"
				+ "            COUNT(*) OVER () AS total_rows\n"
				+ "        FROM cte\n"
				+ "    ) ordered_col6\n"
				+ "    WHERE row_num IN (\n"
				+ "        (total_rows + 1) / 2, \n"
				+ "        total_rows / 2, \n"
				+ "        (total_rows / 2) + 1\n"
				+ "    )\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME, \n"
				+ "    cte.ZONE_CODE, \n"
				+ "    cte.Col2, \n"
				+ "    cte.Col6, \n"
				+ "    SUM(cte1.Col3) OVER (PARTITION BY cte1.ZONE_CODE ORDER BY cte1.ZONE_NAME ASC) AS Col3, \n"
				+ "    SUM(cte1.Col7) OVER (PARTITION BY cte1.ZONE_CODE ORDER BY cte1.ZONE_NAME ASC) AS Col7, \n"
				+ "    (cte.Col6 / NULLIF(\n"
				+ "        (SUM(cte1.Col3) OVER (PARTITION BY cte1.ZONE_CODE ORDER BY cte1.ZONE_NAME ASC) - \n"
				+ "         SUM(cte1.Col7) OVER (PARTITION BY cte1.ZONE_CODE ORDER BY cte1.ZONE_NAME ASC)) + \n"
				+ "         cte.Col2, 0)) * 100 AS total_score,\n"
				+ "    median_cte.median_col6\n"
				+ "FROM \n"
				+ "    cte\n"
				+ "JOIN \n"
				+ "    cte1 ON cte.ZONE_CODE = cte1.ZONE_CODE\n"
				+ "CROSS JOIN \n"
				+ "    median_cte\n"
				+ "ORDER BY \n"
				+ "    total_score desc;";
		return queryGst14aa;
	}
	public String QueryFor_gst10a_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="\n"
				+ "WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        SUM(UNIT_ALLOTTED_NO_LARGE + UNIT_ALLOTTED_NO_MEDIUM + UNIT_ALLOTTED_NO_SMALL) AS Col2, \n"
				+ "        SUM(UNIT_AUDITED_NO_LARGE + UNIT_AUDITED_NO_MEDIUM + UNIT_AUDITED_NO_SMALL) AS Col6\n"
				+ "    FROM \n"
				+ "        mis_dga_gst_adt_1 AS lgl\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        lgl.MM_YYYY = '" + month_date + "'\n"
				+ "    GROUP BY \n"
				+ "        zc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Updated GROUP BY to include COMM_NAME\n"
				+ "),\n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        COALESCE(SUM(UNIT_ALLOTTED_NO_LARGE + UNIT_ALLOTTED_NO_MEDIUM + UNIT_ALLOTTED_NO_SMALL), 0) AS Col3, \n"
				+ "        COALESCE(SUM(UNIT_AUDITED_NO_LARGE + UNIT_AUDITED_NO_MEDIUM + UNIT_AUDITED_NO_SMALL), 0) AS Col7\n"
				+ "    FROM \n"
				+ "        mis_dga_gst_adt_1 AS lgl\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        lgl.MM_YYYY <=  '"+ prev_month_new+"'\n"
				+ "    GROUP BY \n"
				+ "        zc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Updated GROUP BY to include COMM_NAME\n"
				+ "),\n"
				+ "median_cte AS (\n"
				+ "    SELECT \n"
				+ "        AVG(Col6) AS median_col6\n"
				+ "    FROM (\n"
				+ "        SELECT \n"
				+ "            Col6,\n"
				+ "            ROW_NUMBER() OVER (ORDER BY Col6 ASC) AS row_num,\n"
				+ "            COUNT(*) OVER () AS total_rows\n"
				+ "        FROM cte\n"
				+ "    ) ordered_col6\n"
				+ "    WHERE row_num IN (\n"
				+ "        (total_rows + 1) / 2, \n"
				+ "        total_rows / 2, \n"
				+ "        (total_rows / 2) + 1\n"
				+ "    )\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME, \n"
				+ "    cte.ZONE_CODE, \n"
				+ "    cte.COMM_NAME, -- Added COMM_NAME\n"
				+ "    cte.Col2, \n"
				+ "    cte.Col6, \n"
				+ "    SUM(cte1.Col3) OVER (PARTITION BY cte1.ZONE_CODE, cte1.COMM_NAME ORDER BY cte1.ZONE_NAME ASC) AS Col3, -- Updated PARTITION BY to include COMM_NAME\n"
				+ "    SUM(cte1.Col7) OVER (PARTITION BY cte1.ZONE_CODE, cte1.COMM_NAME ORDER BY cte1.ZONE_NAME ASC) AS Col7, -- Updated PARTITION BY to include COMM_NAME\n"
				+ "    (cte.Col6 / NULLIF(\n"
				+ "        (SUM(cte1.Col3) OVER (PARTITION BY cte1.ZONE_CODE, cte1.COMM_NAME ORDER BY cte1.ZONE_NAME ASC) - \n"
				+ "         SUM(cte1.Col7) OVER (PARTITION BY cte1.ZONE_CODE, cte1.COMM_NAME ORDER BY cte1.ZONE_NAME ASC)) + \n"
				+ "         cte.Col2, 0)) * 100 AS total_score,\n"
				+ "    median_cte.median_col6\n"
				+ "FROM \n"
				+ "    cte\n"
				+ "JOIN \n"
				+ "    cte1 ON cte.ZONE_CODE = cte1.ZONE_CODE AND cte.COMM_NAME = cte1.COMM_NAME -- Added COMM_NAME to the join condition\n"
				+ "CROSS JOIN \n"
				+ "    median_cte\n"
				+ "WHERE \n"
				+ "    cte.ZONE_CODE = '" + zone_code + "'\n"
				+ "ORDER BY \n"
				+ "    total_score ASC;\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst10a_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        SUM(UNIT_ALLOTTED_NO_LARGE + UNIT_ALLOTTED_NO_MEDIUM + UNIT_ALLOTTED_NO_SMALL) AS Col2, \n"
				+ "        SUM(UNIT_AUDITED_NO_LARGE + UNIT_AUDITED_NO_MEDIUM + UNIT_AUDITED_NO_SMALL) AS Col6\n"
				+ "    FROM \n"
				+ "        mis_dga_gst_adt_1 AS lgl\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        lgl.MM_YYYY ='" + month_date + "'\n"
				+ "    GROUP BY \n"
				+ "        zc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Updated GROUP BY to include COMM_NAME\n"
				+ "),\n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        COALESCE(SUM(UNIT_ALLOTTED_NO_LARGE + UNIT_ALLOTTED_NO_MEDIUM + UNIT_ALLOTTED_NO_SMALL), 0) AS Col3, \n"
				+ "        COALESCE(SUM(UNIT_AUDITED_NO_LARGE + UNIT_AUDITED_NO_MEDIUM + UNIT_AUDITED_NO_SMALL), 0) AS Col7\n"
				+ "    FROM \n"
				+ "        mis_dga_gst_adt_1 AS lgl\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        lgl.MM_YYYY <= '"+ prev_month_new+"'\n"
				+ "    GROUP BY \n"
				+ "        zc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Updated GROUP BY to include COMM_NAME\n"
				+ "),\n"
				+ "median_cte AS (\n"
				+ "    SELECT \n"
				+ "        AVG(Col6) AS median_col6\n"
				+ "    FROM (\n"
				+ "        SELECT \n"
				+ "            Col6,\n"
				+ "            ROW_NUMBER() OVER (ORDER BY Col6 ASC) AS row_num,\n"
				+ "            COUNT(*) OVER () AS total_rows\n"
				+ "        FROM cte\n"
				+ "    ) ordered_col6\n"
				+ "    WHERE row_num IN (\n"
				+ "        (total_rows + 1) / 2, \n"
				+ "        total_rows / 2, \n"
				+ "        (total_rows / 2) + 1\n"
				+ "    )\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME, \n"
				+ "    cte.ZONE_CODE, \n"
				+ "    cte.COMM_NAME, -- Added COMM_NAME\n"
				+ "    cte.Col2, \n"
				+ "    cte.Col6, \n"
				+ "    SUM(cte1.Col3) OVER (PARTITION BY cte1.ZONE_CODE, cte1.COMM_NAME ORDER BY cte1.ZONE_NAME ASC) AS Col3, -- Updated PARTITION BY to include COMM_NAME\n"
				+ "    SUM(cte1.Col7) OVER (PARTITION BY cte1.ZONE_CODE, cte1.COMM_NAME ORDER BY cte1.ZONE_NAME ASC) AS Col7, -- Updated PARTITION BY to include COMM_NAME\n"
				+ "    (cte.Col6 / NULLIF(\n"
				+ "        (SUM(cte1.Col3) OVER (PARTITION BY cte1.ZONE_CODE, cte1.COMM_NAME ORDER BY cte1.ZONE_NAME ASC) - \n"
				+ "         SUM(cte1.Col7) OVER (PARTITION BY cte1.ZONE_CODE, cte1.COMM_NAME ORDER BY cte1.ZONE_NAME ASC)) + \n"
				+ "         cte.Col2, 0)) * 100 AS total_score,\n"
				+ "    median_cte.median_col6\n"
				+ "FROM \n"
				+ "    cte\n"
				+ "JOIN \n"
				+ "    cte1 ON cte.ZONE_CODE = cte1.ZONE_CODE AND cte.COMM_NAME = cte1.COMM_NAME -- Added COMM_NAME to the join condition\n"
				+ "CROSS JOIN \n"
				+ "    median_cte\n"
				+ "ORDER BY \n"
				+ "    total_score desc;";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst10b_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME,\n"
				+ "        zc.ZONE_CODE,\n"
				+ "        SUM(lgl.AUDIT_PARAS_BREAKUP_6_12_NO_LARGE + lgl.AUDIT_PARAS_BREAKUP_6_12_NO_MEDIUM + lgl.AUDIT_PARAS_BREAKUP_6_12_NO_SMALL) AS Col28,\n"
				+ "        SUM(lgl.AUDIT_PARAS_BREAKUP_MORE_1_YEAR_NO_LARGE + lgl.AUDIT_PARAS_BREAKUP_MORE_1_YEAR_NO_MEDIUM + lgl.AUDIT_PARAS_BREAKUP_MORE_1_YEAR_NO_SMALL) AS Col30,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_FRESH_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_FRESH_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_FRESH_NO_SMALL\n"
				+ "        ), 0) AS Col4,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_RAISED_DEFERRED_BY_MCM_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_DEFERRED_BY_MCM_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_DEFERRED_BY_MCM_NO_SMALL\n"
				+ "        ), 0) AS Col8,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_DEFERRED_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_DEFERRED_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_DEFERRED_NO_SMALL\n"
				+ "        ), 0) AS Col6,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DEPOSIT_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DEPOSIT_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DEPOSIT_NO_SMALL\n"
				+ "        ), 0) AS Col14,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DROPPED_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DROPPED_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DROPPED_NO_SMALL\n"
				+ "        ), 0) AS Col16,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DRC_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DRC_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DRC_NO_SMALL\n"
				+ "        ), 0) AS Col12,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_TRANSFERED_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_TRANSFERED_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_TRANSFERED_NO_SMALL\n"
				+ "        ), 0) AS Col18\n"
				+ "    FROM mis_dga_gst_adt_2 AS lgl\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE lgl.MM_YYYY = '" + month_date + "'\n"
				+ "    GROUP BY zc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "),\n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME,\n"
				+ "        zc.ZONE_CODE,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_CLOSING_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_CLOSING_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_CLOSING_NO_SMALL\n"
				+ "        ), 0) AS Col2\n"
				+ "    FROM mis_dga_gst_adt_2 AS lgl\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE lgl.MM_YYYY = '"+ prev_month_new+"'\n"
				+ "    GROUP BY zc.ZONE_CODE, zc.ZONE_NAME\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME,\n"
				+ "    cte.ZONE_CODE,\n"
				+ "    cte.Col28,\n"
				+ "    cte.Col30,\n"
				+ "    cte.Col4,\n"
				+ "    cte.Col8,\n"
				+ "    cte.Col6,\n"
				+ "    cte.Col14,\n"
				+ "    cte.Col16,\n"
				+ "    cte.Col18,\n"
				+ "    cte1.Col2,\n"
				+ "    cte.Col12,\n"
				+ "    (cte.Col4 + cte.Col6 - cte.Col8) AS Col10,\n"
				+ "    (cte.Col12 + cte.Col14 + cte.Col16 + cte.Col18) AS Col20,\n"
				+ "    (cte1.Col2 + (cte.Col4 + cte.Col6 - cte.Col8) - (cte.Col12 + cte.Col14 + cte.Col16 + cte.Col18)) AS Col22,\n"
				+ "    ((cte.Col28 + cte.Col30) / NULLIF((cte1.Col2 + (cte.Col4 + cte.Col6 - cte.Col8) - (cte.Col12 + cte.Col14 + cte.Col16 + cte.Col18)), 0)) * 100 AS total_score10b\n"
				+ "FROM cte\n"
				+ "LEFT JOIN cte1 ON cte.ZONE_CODE = cte1.ZONE_CODE\n"
				+ "ORDER BY total_score10b ASC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst10b_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME,\n"
				+ "        zc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        SUM(lgl.AUDIT_PARAS_BREAKUP_6_12_NO_LARGE + lgl.AUDIT_PARAS_BREAKUP_6_12_NO_MEDIUM + lgl.AUDIT_PARAS_BREAKUP_6_12_NO_SMALL) AS Col28,\n"
				+ "        SUM(lgl.AUDIT_PARAS_BREAKUP_MORE_1_YEAR_NO_LARGE + lgl.AUDIT_PARAS_BREAKUP_MORE_1_YEAR_NO_MEDIUM + lgl.AUDIT_PARAS_BREAKUP_MORE_1_YEAR_NO_SMALL) AS Col30,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_FRESH_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_FRESH_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_FRESH_NO_SMALL\n"
				+ "        ), 0) AS Col4,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_RAISED_DEFERRED_BY_MCM_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_DEFERRED_BY_MCM_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_DEFERRED_BY_MCM_NO_SMALL\n"
				+ "        ), 0) AS Col8,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_DEFERRED_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_DEFERRED_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_DEFERRED_NO_SMALL\n"
				+ "        ), 0) AS Col6,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DEPOSIT_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DEPOSIT_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DEPOSIT_NO_SMALL\n"
				+ "        ), 0) AS Col14,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DROPPED_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DROPPED_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DROPPED_NO_SMALL\n"
				+ "        ), 0) AS Col16,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DRC_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DRC_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DRC_NO_SMALL\n"
				+ "        ), 0) AS Col12,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_TRANSFERED_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_TRANSFERED_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_TRANSFERED_NO_SMALL\n"
				+ "        ), 0) AS Col18\n"
				+ "    FROM mis_dga_gst_adt_2 AS lgl\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE lgl.MM_YYYY = '" + month_date + "' AND zc.ZONE_CODE ='" + zone_code + "' -- Added condition\n"
				+ "    GROUP BY zc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Added COMM_NAME to GROUP BY\n"
				+ "),\n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME,\n"
				+ "        zc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_CLOSING_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_CLOSING_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_CLOSING_NO_SMALL\n"
				+ "        ), 0) AS Col2\n"
				+ "    FROM mis_dga_gst_adt_2 AS lgl\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE lgl.MM_YYYY = '"+ prev_month_new+"' AND zc.ZONE_CODE = '" + zone_code + "'-- Added condition\n"
				+ "    GROUP BY zc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Added COMM_NAME to GROUP BY\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME,\n"
				+ "    cte.ZONE_CODE,\n"
				+ "    cte.COMM_NAME, -- Added COMM_NAME\n"
				+ "    cte.Col28,\n"
				+ "    cte.Col30,\n"
				+ "    cte.Col4,\n"
				+ "    cte.Col8,\n"
				+ "    cte.Col6,\n"
				+ "    cte.Col14,\n"
				+ "    cte.Col16,\n"
				+ "    cte.Col18,\n"
				+ "    cte1.Col2,\n"
				+ "    cte.Col12,\n"
				+ "    (cte.Col4 + cte.Col6 - cte.Col8) AS Col10,\n"
				+ "    (cte.Col12 + cte.Col14 + cte.Col16 + cte.Col18) AS Col20,\n"
				+ "    (cte1.Col2 + (cte.Col4 + cte.Col6 - cte.Col8) - (cte.Col12 + cte.Col14 + cte.Col16 + cte.Col18)) AS Col22,\n"
				+ "    ((cte.Col28 + cte.Col30) / NULLIF((cte1.Col2 + (cte.Col4 + cte.Col6 - cte.Col8) - (cte.Col12 + cte.Col14 + cte.Col16 + cte.Col18)), 0)) * 100 AS total_score10b\n"
				+ "FROM cte\n"
				+ "LEFT JOIN cte1 ON cte.ZONE_CODE = cte1.ZONE_CODE AND cte.COMM_NAME = cte1.COMM_NAME -- Ensure matching by COMM_NAME\n"
				+ "ORDER BY total_score10b ASC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst10b_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME,\n"
				+ "        zc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        SUM(lgl.AUDIT_PARAS_BREAKUP_6_12_NO_LARGE + lgl.AUDIT_PARAS_BREAKUP_6_12_NO_MEDIUM + lgl.AUDIT_PARAS_BREAKUP_6_12_NO_SMALL) AS Col28,\n"
				+ "        SUM(lgl.AUDIT_PARAS_BREAKUP_MORE_1_YEAR_NO_LARGE + lgl.AUDIT_PARAS_BREAKUP_MORE_1_YEAR_NO_MEDIUM + lgl.AUDIT_PARAS_BREAKUP_MORE_1_YEAR_NO_SMALL) AS Col30,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_FRESH_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_FRESH_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_FRESH_NO_SMALL\n"
				+ "        ), 0) AS Col4,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_RAISED_DEFERRED_BY_MCM_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_DEFERRED_BY_MCM_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_DEFERRED_BY_MCM_NO_SMALL\n"
				+ "        ), 0) AS Col8,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_DEFERRED_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_DEFERRED_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_RAISED_PRESENTED_DEFERRED_NO_SMALL\n"
				+ "        ), 0) AS Col6,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DEPOSIT_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DEPOSIT_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DEPOSIT_NO_SMALL\n"
				+ "        ), 0) AS Col14,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DROPPED_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DROPPED_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DROPPED_NO_SMALL\n"
				+ "        ), 0) AS Col16,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DRC_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DRC_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_DRC_NO_SMALL\n"
				+ "        ), 0) AS Col12,\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_SETTLED_TRANSFERED_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_TRANSFERED_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_SETTLED_TRANSFERED_NO_SMALL\n"
				+ "        ), 0) AS Col18\n"
				+ "    FROM mis_dga_gst_adt_2 AS lgl\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE lgl.MM_YYYY = '" + month_date + "'\n"
				+ "    GROUP BY zc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Added COMM_NAME to GROUP BY\n"
				+ "),\n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME,\n"
				+ "        zc.ZONE_CODE,\n"
				+ "        cc.COMM_NAME, -- Added COMM_NAME\n"
				+ "        COALESCE(SUM(\n"
				+ "            lgl.AUDIT_PARAS_CLOSING_NO_LARGE + \n"
				+ "            lgl.AUDIT_PARAS_CLOSING_NO_MEDIUM + \n"
				+ "            lgl.AUDIT_PARAS_CLOSING_NO_SMALL\n"
				+ "        ), 0) AS Col2\n"
				+ "    FROM mis_dga_gst_adt_2 AS lgl\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON lgl.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE lgl.MM_YYYY = '"+ prev_month_new+"'\n"
				+ "    GROUP BY zc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME -- Added COMM_NAME to GROUP BY\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME,\n"
				+ "    cte.ZONE_CODE,\n"
				+ "    cte.COMM_NAME, -- Added COMM_NAME\n"
				+ "    cte.Col28,\n"
				+ "    cte.Col30,\n"
				+ "    cte.Col4,\n"
				+ "    cte.Col8,\n"
				+ "    cte.Col6,\n"
				+ "    cte.Col14,\n"
				+ "    cte.Col16,\n"
				+ "    cte.Col18,\n"
				+ "    cte1.Col2,\n"
				+ "    cte.Col12,\n"
				+ "    (cte.Col4 + cte.Col6 - cte.Col8) AS Col10,\n"
				+ "    (cte.Col12 + cte.Col14 + cte.Col16 + cte.Col18) AS Col20,\n"
				+ "    (cte1.Col2 + (cte.Col4 + cte.Col6 - cte.Col8) - (cte.Col12 + cte.Col14 + cte.Col16 + cte.Col18)) AS Col22,\n"
				+ "    ((cte.Col28 + cte.Col30) / NULLIF((cte1.Col2 + (cte.Col4 + cte.Col6 - cte.Col8) - (cte.Col12 + cte.Col14 + cte.Col16 + cte.Col18)), 0)) * 100 AS total_score10b\n"
				+ "FROM cte\n"
				+ "LEFT JOIN cte1 ON cte.ZONE_CODE = cte1.ZONE_CODE AND cte.COMM_NAME = cte1.COMM_NAME -- Ensure matching by COMM_NAME\n"
				+ "ORDER BY total_score10b ASC;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst10c_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa="WITH CumulativeData AS (\n" +
				"    SELECT cc.ZONE_CODE,zc.ZONE_NAME,scr.MM_YYYY,\n" +
				"        SUM(TOTAL_TAX_RECOVERED_TAX_AMT_LARGE + TOTAL_TAX_RECOVERED_TAX_AMT_MEDIUM + TOTAL_TAX_RECOVERED_TAX_AMT_SMALL) \n" +
				"            OVER (PARTITION BY cc.ZONE_CODE, zc.ZONE_NAME ORDER BY scr.MM_YYYY) AS col22,\n" +
				"        SUM(TOTAL_TAX_RECOVERED_INTEREST_AMT_LARGE + TOTAL_TAX_RECOVERED_INTEREST_AMT_MEDIUM + TOTAL_TAX_RECOVERED_INTEREST_AMT_SMALL) \n" +
				"            OVER (PARTITION BY cc.ZONE_CODE, zc.ZONE_NAME ORDER BY scr.MM_YYYY) AS col23,\n" +
				"        SUM(TOTAL_TAX_RECOVERED_OTHERS_AMT_LARGE + TOTAL_TAX_RECOVERED_OTHERS_AMT_MEDIUM + TOTAL_TAX_RECOVERED_OTHERS_AMT_SMALL) \n" +
				"            OVER (PARTITION BY cc.ZONE_CODE, zc.ZONE_NAME ORDER BY scr.MM_YYYY) AS col24,\n" +
				"        SUM(TOTAL_TAX_RECOVERED_ITC_AMT_LARGE + TOTAL_TAX_RECOVERED_ITC_AMT_MEDIUM + TOTAL_TAX_RECOVERED_ITC_AMT_SMALL) \n" +
				"            OVER (PARTITION BY cc.ZONE_CODE, zc.ZONE_NAME ORDER BY scr.MM_YYYY) AS col26,\n" +
				"        SUM(TOTAL_TAX_DETECTED_TAX_AMT_LARGE + TOTAL_TAX_DETECTED_TAX_AMT_MEDIUM + TOTAL_TAX_DETECTED_TAX_AMT_SMALL) \n" +
				"            OVER (PARTITION BY cc.ZONE_CODE, zc.ZONE_NAME ORDER BY scr.MM_YYYY) AS col13,\n" +
				"        SUM(TOTAL_TAX_DETECTED_OTHERS_AMT_LARGE + TOTAL_TAX_DETECTED_OTHERS_AMT_MEDIUM + TOTAL_TAX_DETECTED_OTHERS_AMT_SMALL) \n" +
				"            OVER (PARTITION BY cc.ZONE_CODE, zc.ZONE_NAME ORDER BY scr.MM_YYYY) AS col14\n" +
				"    FROM mis_gst_commcode AS cc\n" +
				"    RIGHT JOIN mis_dga_gst_adt_3 AS scr ON cc.COMM_CODE = scr.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE scr.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "'\n" +
				"),\n" +
				"AggregatedData AS (\n" +
				"    SELECT ZONE_CODE,ZONE_NAME,MM_YYYY,\n" +
				"        (MAX(col22) + MAX(col23) + MAX(col24) + MAX(col26)) AS col27,(MAX(col13) + MAX(col14)) AS col15,\n" +
				"        CONCAT((MAX(col22) + MAX(col23) + MAX(col24) + MAX(col26)), '/', MAX(col13) + MAX(col14)) AS absvl\n" +
				"    FROM CumulativeData WHERE MM_YYYY = '" + month_date + "' GROUP BY ZONE_CODE, ZONE_NAME, MM_YYYY\n" +
				"),\n" +
				"RankedData AS (\n" +
				"    SELECT *, ROW_NUMBER() OVER (ORDER BY col27) AS row_num, COUNT(*) OVER () AS total_rows\n" +
				"    FROM AggregatedData\n" +
				"),\n" +
				"MedianData AS (\n" +
				"    SELECT CASE WHEN MOD(total_rows, 2) = 1 THEN (SELECT col27 FROM RankedData WHERE row_num = (total_rows + 1) / 2)\n" +
				"            ELSE (SELECT AVG(col27) FROM RankedData WHERE row_num IN (total_rows / 2, total_rows / 2 + 1))\n" +
				"        END AS median10c FROM RankedData LIMIT 1\n" +
				") SELECT ad.*, md.median10c FROM AggregatedData AS ad CROSS JOIN MedianData AS md;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst10c_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa="WITH ComputedData AS (\n" +
				"    SELECT cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME,\n" +
				"        SUM(tc.TOTAL_TAX_RECOVERED_TAX_AMT_LARGE + tc.TOTAL_TAX_RECOVERED_TAX_AMT_MEDIUM + tc.TOTAL_TAX_RECOVERED_TAX_AMT_SMALL) AS col22,\n" +
				"        SUM(tc.TOTAL_TAX_RECOVERED_INTEREST_AMT_LARGE + tc.TOTAL_TAX_RECOVERED_INTEREST_AMT_MEDIUM + tc.TOTAL_TAX_RECOVERED_INTEREST_AMT_SMALL) AS col23,\n" +
				"        SUM(tc.TOTAL_TAX_RECOVERED_OTHERS_AMT_LARGE + tc.TOTAL_TAX_RECOVERED_OTHERS_AMT_MEDIUM + tc.TOTAL_TAX_RECOVERED_OTHERS_AMT_SMALL) AS col24,\n" +
				"        SUM(tc.TOTAL_TAX_RECOVERED_ITC_AMT_LARGE + tc.TOTAL_TAX_RECOVERED_ITC_AMT_MEDIUM + tc.TOTAL_TAX_RECOVERED_ITC_AMT_SMALL) AS col26,\n" +
				"        SUM(tc.TOTAL_TAX_DETECTED_TAX_AMT_LARGE + tc.TOTAL_TAX_DETECTED_TAX_AMT_MEDIUM + tc.TOTAL_TAX_DETECTED_TAX_AMT_SMALL) AS col13,\n" +
				"        SUM(tc.TOTAL_TAX_DETECTED_OTHERS_AMT_LARGE + tc.TOTAL_TAX_DETECTED_OTHERS_AMT_MEDIUM + tc.TOTAL_TAX_DETECTED_OTHERS_AMT_SMALL) AS col14\n" +
				"    FROM mis_gst_commcode AS cc\n" +
				"    RIGHT JOIN mis_dga_gst_adt_3 AS tc ON cc.COMM_CODE = tc.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE tc.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "'\n" +
				"    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME\n" +
				"),\n" +
				"RankedData AS (\n" +
				"    SELECT ZONE_CODE, ZONE_NAME, COMM_NAME,\n" +
				"        (col22 + col23 + col24 + col26) AS col27,(col13 + col14) AS col15,\n" +
				"        (col22 + col23 + col24 + col26) / NULLIF((col13 + col14), 0) AS totalScore,\n" +
				"        CONCAT((col22 + col23 + col24 + col26), '/', (col13 + col14)) AS absvl,\n" +
				"        ROW_NUMBER() OVER (ORDER BY (col22 + col23 + col24 + col26)) AS rn,\n" +
				"        COUNT(*) OVER () AS total_rows\n" +
				"    FROM ComputedData\n" +
				"),\n" +
				"MedianCalc AS (\n" +
				"    SELECT CASE WHEN total_rows % 2 = 1 THEN \n" +
				"                (SELECT col27 FROM RankedData WHERE rn = (total_rows + 1) / 2)\n" +
				"            ELSE \n" +
				"                ((SELECT col27 FROM RankedData WHERE rn = total_rows / 2) +\n" +
				"                 (SELECT col27 FROM RankedData WHERE rn = total_rows / 2 + 1)) / 2.0\n" +
				"        END AS median10c\n" +
				"    FROM (SELECT DISTINCT total_rows FROM RankedData) AS totalRowInfo\n" +
				")\n" +
				"SELECT r.ZONE_CODE,r.ZONE_NAME,r.COMM_NAME,r.col27,r.col15,r.totalScore,r.absvl,m.median10c\n" +
				"FROM RankedData r\n" +
				"CROSS JOIN MedianCalc m\n" +
				"WHERE r.ZONE_CODE = '" + zone_code + "';";
		return queryGst14aa;
	}
	public String QueryFor_gst10c_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String start_date=DateCalculate.getFinancialYearStart(month_date);
		String queryGst14aa="WITH ComputedData AS (\n" +
				"    SELECT cc.ZONE_CODE,zc.ZONE_NAME,cc.COMM_NAME,\n" +
				"        SUM(tc.TOTAL_TAX_RECOVERED_TAX_AMT_LARGE + tc.TOTAL_TAX_RECOVERED_TAX_AMT_MEDIUM + tc.TOTAL_TAX_RECOVERED_TAX_AMT_SMALL) AS col22,\n" +
				"        SUM(tc.TOTAL_TAX_RECOVERED_INTEREST_AMT_LARGE + tc.TOTAL_TAX_RECOVERED_INTEREST_AMT_MEDIUM + tc.TOTAL_TAX_RECOVERED_INTEREST_AMT_SMALL) AS col23,\n" +
				"        SUM(tc.TOTAL_TAX_RECOVERED_OTHERS_AMT_LARGE + tc.TOTAL_TAX_RECOVERED_OTHERS_AMT_MEDIUM + tc.TOTAL_TAX_RECOVERED_OTHERS_AMT_SMALL) AS col24,\n" +
				"        SUM(tc.TOTAL_TAX_RECOVERED_ITC_AMT_LARGE + tc.TOTAL_TAX_RECOVERED_ITC_AMT_MEDIUM + tc.TOTAL_TAX_RECOVERED_ITC_AMT_SMALL) AS col26,\n" +
				"        SUM(tc.TOTAL_TAX_DETECTED_TAX_AMT_LARGE + tc.TOTAL_TAX_DETECTED_TAX_AMT_MEDIUM + tc.TOTAL_TAX_DETECTED_TAX_AMT_SMALL) AS col13,\n" +
				"        SUM(tc.TOTAL_TAX_DETECTED_OTHERS_AMT_LARGE + tc.TOTAL_TAX_DETECTED_OTHERS_AMT_MEDIUM + tc.TOTAL_TAX_DETECTED_OTHERS_AMT_SMALL) AS col14\n" +
				"    FROM mis_gst_commcode AS cc\n" +
				"    RIGHT JOIN mis_dga_gst_adt_3 AS tc ON cc.COMM_CODE = tc.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON zc.ZONE_CODE = cc.ZONE_CODE\n" +
				"    WHERE tc.MM_YYYY BETWEEN '" + start_date + "' AND '" + month_date + "' \n" +
				"    GROUP BY cc.ZONE_CODE, zc.ZONE_NAME, cc.COMM_NAME\n" +
				"),\n" +
				"RankedData AS (\n" +
				"    SELECT ZONE_CODE, ZONE_NAME,COMM_NAME,(col22 + col23 + col24 + col26) AS col27,(col13 + col14) AS col15,\n" +
				"        (col22 + col23 + col24 + col26) / NULLIF((col13 + col14), 0) AS totalScore,CONCAT((col22 + col23 + col24 + col26), '/', (col13 + col14)) AS absvl,\n" +
				"        ROW_NUMBER() OVER (ORDER BY (col22 + col23 + col24 + col26)) AS rn,COUNT(*) OVER () AS total_rows\n" +
				"    FROM ComputedData\n" +
				"),\n" +
				"MedianCalc AS (\n" +
				"    SELECT CASE WHEN total_rows % 2 = 1 THEN \n" +
				"                (SELECT col27  FROM RankedData  WHERE rn = (total_rows + 1) / 2)\n" +
				"            ELSE ((SELECT col27  FROM RankedData WHERE rn = total_rows / 2) +\n" +
				"                 (SELECT col27 FROM RankedData WHERE rn = total_rows / 2 + 1)) / 2.0\n" +
				"        END AS median10c\n" +
				"    FROM (SELECT DISTINCT total_rows FROM RankedData) AS totalRowInfo\n" +
				")\n" +
				"SELECT r.ZONE_CODE,r.ZONE_NAME,r.COMM_NAME,r.col27,r.col15,r.totalScore,r.absvl,m.median10c\n" +
				"FROM RankedData r\n" +
				"CROSS JOIN MedianCalc m;";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst1od_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="";
		return queryGst14aa;
	}
	public String QueryFor_gst10d_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="";
		return queryGst14aa;
	}
	public String QueryFor_gst10d_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gs11a_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        SUM(DEPARTMENT_DISPOSAL_NO + TAXPAYER_DISPOSAL_NO) AS col10 \n"
				+ "    FROM mis_dla_gst_lgl_1 11a \n"
				+ "    LEFT JOIN mis_gst_commcode cc \n"
				+ "        ON 11a.COMM_CODE = cc.COMM_CODE \n"
				+ "    LEFT JOIN mis_gst_zonecode zc \n"
				+ "        ON cc.ZONE_CODE = zc.ZONE_CODE \n"
				+ "    WHERE 11a.MM_YYYY = '"+ month_date+"' AND FORUM_CODE = 6 \n"
				+ "    GROUP BY zc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "), \n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        SUM(DEPARTMENT_CLOSING_NO + TAXPAYER_CLOSING_NO) AS col4 \n"
				+ "    FROM mis_dla_gst_lgl_1 11a \n"
				+ "    LEFT JOIN mis_gst_commcode cc \n"
				+ "        ON 11a.COMM_CODE = cc.COMM_CODE \n"
				+ "    LEFT JOIN mis_gst_zonecode zc \n"
				+ "        ON cc.ZONE_CODE = zc.ZONE_CODE \n"
				+ "    WHERE 11a.MM_YYYY = '"+ prev_month_new+"' AND FORUM_CODE = 6 \n"
				+ "    GROUP BY zc.ZONE_CODE, zc.ZONE_NAME\n"
				+ "), \n"
				+ "median_cte AS (\n"
				+ "    SELECT \n"
				+ "        AVG(col10) AS median_11a\n"
				+ "    FROM (\n"
				+ "        SELECT col10, \n"
				+ "               ROW_NUMBER() OVER (ORDER BY col10) AS rn,\n"
				+ "               COUNT(*) OVER () AS cnt\n"
				+ "        FROM cte\n"
				+ "    ) AS temp\n"
				+ "    WHERE rn IN (FLOOR((cnt + 1) / 2), CEIL((cnt + 1) / 2))\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME, \n"
				+ "    cte.ZONE_CODE, \n"
				+ "    cte.col10, \n"
				+ "    cte1.col4, \n"
				+ "    (cte.col10 / cte1.col4) AS total_score,\n"
				+ "    median_cte.median_11a\n"
				+ "FROM cte \n"
				+ "INNER JOIN cte1 \n"
				+ "    ON cte.ZONE_CODE = cte1.ZONE_CODE\n"
				+ "CROSS JOIN median_cte\n"
				+ "ORDER BY total_score DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gs11a_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, \n"
				+ "        cc.COMM_CODE, \n"
				+ "        (DEPARTMENT_DISPOSAL_NO + TAXPAYER_DISPOSAL_NO) AS col10 \n"
				+ "    FROM mis_dla_gst_lgl_1 AS 11a\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE \n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE \n"
				+ "    WHERE 11a.MM_YYYY = '" + month_date + "' \n"
				+ "      AND FORUM_CODE = 6\n"
				+ "      AND zc.ZONE_CODE = '" + zone_code + "' -- Apply condition here\n"
				+ "), \n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, \n"
				+ "        cc.COMM_CODE, \n"
				+ "        (DEPARTMENT_CLOSING_NO + TAXPAYER_CLOSING_NO) AS col4 \n"
				+ "    FROM mis_dla_gst_lgl_1 AS 11a\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE \n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE \n"
				+ "    WHERE 11a.MM_YYYY = '"+ prev_month_new+"' \n"
				+ "      AND FORUM_CODE = 6\n"
				+ "      AND zc.ZONE_CODE ='" + zone_code + "' -- Apply condition here\n"
				+ "),\n"
				+ "median_cte AS (\n"
				+ "    SELECT \n"
				+ "        AVG(col10) AS median_11a\n"
				+ "    FROM (\n"
				+ "        SELECT col10, \n"
				+ "               ROW_NUMBER() OVER (ORDER BY col10) AS rn,\n"
				+ "               COUNT(*) OVER () AS cnt\n"
				+ "        FROM cte\n"
				+ "    ) AS temp\n"
				+ "    WHERE rn IN (FLOOR((cnt + 1) / 2), CEIL((cnt + 1) / 2))\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME, \n"
				+ "    cte.ZONE_CODE, \n"
				+ "    cte.COMM_NAME, \n"
				+ "    cte.COMM_CODE, \n"
				+ "    cte.col10, \n"
				+ "    cte1.col4, \n"
				+ "    (cte.col10 / cte1.col4) AS total_score,\n"
				+ "    median_cte.median_11a\n"
				+ "FROM cte\n"
				+ "INNER JOIN cte1 ON cte.COMM_CODE = cte1.COMM_CODE\n"
				+ "CROSS JOIN median_cte\n"
				+ "ORDER BY total_score DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gs11a_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, \n"
				+ "        cc.COMM_CODE, \n"
				+ "        (DEPARTMENT_DISPOSAL_NO + TAXPAYER_DISPOSAL_NO) AS col10 \n"
				+ "    FROM mis_dla_gst_lgl_1 AS 11a\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE \n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE \n"
				+ "    WHERE 11a.MM_YYYY = '"+ month_date+"' AND FORUM_CODE = 6\n"
				+ "), \n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, \n"
				+ "        cc.COMM_CODE, \n"
				+ "        (DEPARTMENT_CLOSING_NO + TAXPAYER_CLOSING_NO) AS col4 \n"
				+ "    FROM mis_dla_gst_lgl_1 AS 11a\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE \n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE \n"
				+ "    WHERE 11a.MM_YYYY = '"+ prev_month_new+"' AND FORUM_CODE = 6\n"
				+ "),\n"
				+ "median_cte AS (\n"
				+ "    SELECT \n"
				+ "        AVG(col10) AS median_11a\n"
				+ "    FROM (\n"
				+ "        SELECT col10, \n"
				+ "               ROW_NUMBER() OVER (ORDER BY col10) AS rn,\n"
				+ "               COUNT(*) OVER () AS cnt\n"
				+ "        FROM cte\n"
				+ "    ) AS temp\n"
				+ "    WHERE rn IN (FLOOR((cnt + 1) / 2), CEIL((cnt + 1) / 2))\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME, \n"
				+ "    cte.ZONE_CODE, \n"
				+ "    cte.COMM_NAME, \n"
				+ "    cte.COMM_CODE, \n"
				+ "    cte.col10, \n"
				+ "    cte1.col4, \n"
				+ "    (cte.col10 / cte1.col4) AS total_score,\n"
				+ "    median_cte.median_11a\n"
				+ "FROM cte\n"
				+ "INNER JOIN cte1 ON cte.COMM_CODE = cte1.COMM_CODE\n"
				+ "CROSS JOIN median_cte\n"
				+ "ORDER BY total_score DESC;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst11b_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n" +
				"    SELECT zc.ZONE_NAME,\n" +
				"           zc.ZONE_CODE,\n" +
				"           SUM(11a.DEPARTMENT_CLOSING_NO + 11a.TAXPAYER_CLOSING_NO) AS col10A,\n" +
				"           SUM(11a.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11a.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12A\n" +
				"    FROM mis_dla_gst_lgl_1a AS 11a\n" +
				"    LEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n" +
				"    WHERE 11a.MM_YYYY = '" + month_date + "' AND FORUM_CODE = 6\n" +
				"    GROUP BY zc.ZONE_CODE\n" +
				"),\n" +
				"cte1 AS (\n" +
				"    SELECT zc.ZONE_NAME,\n" +
				"           zc.ZONE_CODE,\n" +
				"           SUM(11b.DEPARTMENT_CLOSING_NO + 11b.TAXPAYER_CLOSING_NO) AS col10B,\n" +
				"           SUM(11b.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11b.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12B\n" +
				"    FROM mis_dla_gst_lgl_1b AS 11b\n" +
				"    LEFT JOIN mis_gst_commcode AS cc ON 11b.COMM_CODE = cc.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n" +
				"    WHERE 11b.MM_YYYY = '" + month_date + "' AND FORUM_CODE = 6\n" +
				"    GROUP BY zc.ZONE_CODE\n" +
				")\n" +
				"SELECT cte.ZONE_NAME,\n" +
				"       cte.ZONE_CODE,\n" +
				"       cte.col10A,\n" +
				"       cte.col12A,\n" +
				"       cte1.col10B,\n" +
				"       cte1.col12B,\n" +
				"       (cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B) AS numerator,\n" +
				"       (cte.col10A + cte1.col10B) AS denominator,\n" +
				"       CASE \n" +
				"           WHEN (cte.col10A + cte1.col10B) = 0 THEN NULL \n" +
				"           ELSE (((cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B)) / (cte.col10A + cte1.col10B)) \n" +
				"       END AS total_score \n" +
				"FROM cte\n" +
				"INNER JOIN cte1 ON cte.ZONE_CODE = cte1.ZONE_CODE\n" +
				"ORDER BY total_score ASC;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst11b_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH cte AS (\n" +
				"    SELECT \n" +
				"        zc.ZONE_NAME, \n" +
				"        zc.ZONE_CODE, \n" +
				"        cc.COMM_NAME, \n" +
				"        cc.COMM_CODE, \n" +
				"        (11a.DEPARTMENT_CLOSING_NO + 11a.TAXPAYER_CLOSING_NO) AS col10A, \n" +
				"        (11a.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11a.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12A \n" +
				"    FROM \n" +
				"        mis_dla_gst_lgl_1a AS 11a\n" +
				"    LEFT JOIN \n" +
				"        mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE\n" +
				"    LEFT JOIN \n" +
				"        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n" +
				"    WHERE \n" +
				"        11a.MM_YYYY = '" + month_date + "'\n" +
				"        AND FORUM_CODE = 6 \n" +
				"        AND cc.ZONE_CODE = '" + zone_code + "'\n" +
				"), \n" +
				"cte1 AS (\n" +
				"    SELECT \n" +
				"        zc.ZONE_NAME, \n" +
				"        zc.ZONE_CODE, \n" +
				"        cc.COMM_NAME, \n" +
				"        cc.COMM_CODE, \n" +
				"        (11b.DEPARTMENT_CLOSING_NO + 11b.TAXPAYER_CLOSING_NO) AS col10B, \n" +
				"        (11b.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11b.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12B \n" +
				"    FROM \n" +
				"        mis_dla_gst_lgl_1b AS 11b\n" +
				"    LEFT JOIN \n" +
				"        mis_gst_commcode AS cc ON 11b.COMM_CODE = cc.COMM_CODE\n" +
				"    LEFT JOIN \n" +
				"        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n" +
				"    WHERE \n" +
				"        11b.MM_YYYY = '" + month_date + "'\n" +
				"        AND FORUM_CODE = 6 \n" +
				"        AND cc.ZONE_CODE = '" + zone_code + "'\n" +
				") \n" +
				"SELECT \n" +
				"    cte.ZONE_NAME, \n" +
				"    cte.ZONE_CODE, \n" +
				"    cte.COMM_NAME, \n" +
				"    cte.COMM_CODE, \n" +
				"    cte.col10A, \n" +
				"    cte.col12A, \n" +
				"    cte1.col10B, \n" +
				"    cte1.col12B, \n" +
				"    (cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B) AS numerator, \n" +
				"    (cte.col10A + cte1.col10B) AS denominator, \n" +
				"    (((cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B)) / (cte.col10A + cte1.col10B)) AS total_score \n" +
				"FROM \n" +
				"    cte \n" +
				"INNER JOIN \n" +
				"    cte1 ON cte.COMM_CODE = cte1.COMM_CODE\n" +
				"    order by total_score asc;\n";
		return queryGst14aa;
	}
	public String QueryFor_gst11b_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH cte AS (\n" +
				"    SELECT zc.ZONE_NAME, zc.ZONE_CODE, cc.COMM_NAME, cc.COMM_CODE,\n" +
				"           (11a.DEPARTMENT_CLOSING_NO + 11a.TAXPAYER_CLOSING_NO) AS col10A,\n" +
				"           (11a.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11a.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12A\n" +
				"    FROM mis_dla_gst_lgl_1a AS 11a\n" +
				"    LEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n" +
				"    WHERE 11a.MM_YYYY = '" + month_date + "' AND FORUM_CODE = 6\n" +
				"),\n" +
				"cte1 AS (\n" +
				"    SELECT zc.ZONE_NAME, zc.ZONE_CODE, cc.COMM_NAME, cc.COMM_CODE,\n" +
				"           (11b.DEPARTMENT_CLOSING_NO + 11b.TAXPAYER_CLOSING_NO) AS col10B,\n" +
				"           (11b.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11b.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12B\n" +
				"    FROM mis_dla_gst_lgl_1b AS 11b\n" +
				"    LEFT JOIN mis_gst_commcode AS cc ON 11b.COMM_CODE = cc.COMM_CODE\n" +
				"    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n" +
				"    WHERE 11b.MM_YYYY = '" + month_date + "' AND FORUM_CODE = 6\n" +
				")\n" +
				"SELECT cte.ZONE_NAME, cte.ZONE_CODE, cte.COMM_NAME, cte.COMM_CODE, \n" +
				"       cte.col10A, cte.col12A, cte1.col10B, cte1.col12B,\n" +
				"       (cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B) AS numerator,\n" +
				"       (cte.col10A + cte1.col10B) AS denominator,\n" +
				"       (((cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B)) / (cte.col10A + cte1.col10B)) AS total_score\n" +
				"FROM cte\n" +
				"INNER JOIN cte1 ON cte.COMM_CODE = cte1.COMM_CODE\n" +
				"order by total_score asc;\n";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst11c_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE,\n"
				+ "        SUM(11a.DEPARTMENT_DISPOSAL_NO + 11a.TAXPAYER_DISPOSAL_NO) AS col10\n"
				+ "    FROM mis_dla_gst_lgl_1 AS 11a\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE 11a.MM_YYYY = '" + month_date + "' AND 11a.FORUM_CODE = 7\n"
				+ "    GROUP BY zc.ZONE_NAME, zc.ZONE_CODE\n"
				+ "),\n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE,\n"
				+ "        SUM(11a.DEPARTMENT_CLOSING_NO + 11a.TAXPAYER_CLOSING_NO) AS col4\n"
				+ "    FROM mis_dla_gst_lgl_1 AS 11a\n"
				+ "    LEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE 11a.MM_YYYY = '" + prev_month_new + "' AND 11a.FORUM_CODE = 7\n"
				+ "    GROUP BY zc.ZONE_NAME, zc.ZONE_CODE\n"
				+ "),\n"
				+ "median_cte AS (\n"
				+ "    SELECT \n"
				+ "        AVG(col10) AS median_11c\n"
				+ "    FROM (\n"
				+ "        SELECT col10, \n"
				+ "               ROW_NUMBER() OVER (ORDER BY col10) AS rn,\n"
				+ "               COUNT(*) OVER () AS cnt\n"
				+ "        FROM cte\n"
				+ "    ) AS temp\n"
				+ "    WHERE rn IN (FLOOR((cnt + 1) / 2), CEIL((cnt + 1) / 2))\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME, \n"
				+ "    cte.ZONE_CODE, \n"
				+ "    cte.col10, \n"
				+ "    cte1.col4, \n"
				+ "    (cte.col10 / NULLIF(cte1.col4, 0)) AS total_score,\n"
				+ "    median_cte.median_11c\n"
				+ "FROM cte\n"
				+ "INNER JOIN cte1 ON cte.ZONE_CODE = cte1.ZONE_CODE\n"
				+ "CROSS JOIN median_cte\n"
				+ "ORDER BY total_score DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst11c_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, \n"
				+ "        cc.COMM_CODE,\n"
				+ "        (DEPARTMENT_DISPOSAL_NO + TAXPAYER_DISPOSAL_NO) AS col10\n"
				+ "    FROM \n"
				+ "        mis_dla_gst_lgl_1 AS t1\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON t1.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        t1.MM_YYYY = '" + month_date + "'\n"
				+ "        AND t1.FORUM_CODE = 7\n"
				+ "),\n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, \n"
				+ "        cc.COMM_CODE,\n"
				+ "        (DEPARTMENT_CLOSING_NO + TAXPAYER_CLOSING_NO) AS col4\n"
				+ "    FROM \n"
				+ "        mis_dla_gst_lgl_1 AS t2\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON t2.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        t2.MM_YYYY = '" + prev_month_new + "' \n"
				+ "        AND t2.FORUM_CODE = 7\n"
				+ "        AND zc.ZONE_CODE = '" + zone_code + "'  -- Keep this condition to filter by ZONE_CODE\n"
				+ "),\n"
				+ "median_cte AS (\n"
				+ "    SELECT \n"
				+ "        AVG(col10) AS median_11c\n"
				+ "    FROM (\n"
				+ "        SELECT col10, \n"
				+ "               ROW_NUMBER() OVER (ORDER BY col10) AS rn,\n"
				+ "               COUNT(*) OVER () AS cnt\n"
				+ "        FROM cte\n"
				+ "    ) AS temp\n"
				+ "    WHERE rn IN (FLOOR((cnt + 1) / 2), CEIL((cnt + 1) / 2))\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME, \n"
				+ "    cte.ZONE_CODE, \n"
				+ "    cte.COMM_NAME, \n"
				+ "    cte.COMM_CODE, \n"
				+ "    cte.col10, \n"
				+ "    cte1.col4,\n"
				+ "    (cte.col10 / NULLIF(cte1.col4, 0)) AS total_score,\n"
				+ "    median_cte.median_11c\n"
				+ "FROM \n"
				+ "    cte\n"
				+ "INNER JOIN \n"
				+ "    cte1 ON cte.COMM_CODE = cte1.COMM_CODE\n"
				+ "CROSS JOIN \n"
				+ "    median_cte\n"
				+ "ORDER BY \n"
				+ "    total_score DESC;\n"
				+ "";
		return queryGst14aa;
	}
	public String QueryFor_gst11c_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, \n"
				+ "        cc.COMM_CODE,\n"
				+ "        (DEPARTMENT_DISPOSAL_NO + TAXPAYER_DISPOSAL_NO) AS col10\n"
				+ "    FROM \n"
				+ "        mis_dla_gst_lgl_1 AS t1\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON t1.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        t1.MM_YYYY =  '" + month_date + "' \n"
				+ "        AND t1.FORUM_CODE = 7\n"
				+ "),\n"
				+ "cte1 AS (\n"
				+ "    SELECT \n"
				+ "        zc.ZONE_NAME, \n"
				+ "        zc.ZONE_CODE, \n"
				+ "        cc.COMM_NAME, \n"
				+ "        cc.COMM_CODE,\n"
				+ "        (DEPARTMENT_CLOSING_NO + TAXPAYER_CLOSING_NO) AS col4\n"
				+ "    FROM \n"
				+ "        mis_dla_gst_lgl_1 AS t2\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_commcode AS cc ON t2.COMM_CODE = cc.COMM_CODE\n"
				+ "    LEFT JOIN \n"
				+ "        mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n"
				+ "    WHERE \n"
				+ "        t2.MM_YYYY = '" + prev_month_new + "' \n"
				+ "        AND t2.FORUM_CODE = 7\n"
				+ "),\n"
				+ "median_cte AS (\n"
				+ "    SELECT \n"
				+ "        AVG(col10) AS median_11c\n"
				+ "    FROM (\n"
				+ "        SELECT col10, \n"
				+ "               ROW_NUMBER() OVER (ORDER BY col10) AS rn,\n"
				+ "               COUNT(*) OVER () AS cnt\n"
				+ "        FROM cte\n"
				+ "    ) AS temp\n"
				+ "    WHERE rn IN (FLOOR((cnt + 1) / 2), CEIL((cnt + 1) / 2))\n"
				+ ")\n"
				+ "SELECT \n"
				+ "    cte.ZONE_NAME, \n"
				+ "    cte.ZONE_CODE, \n"
				+ "    cte.COMM_NAME, \n"
				+ "    cte.COMM_CODE, \n"
				+ "    cte.col10, \n"
				+ "    cte1.col4,\n"
				+ "    (cte.col10 / NULLIF(cte1.col4, 0)) AS total_score,\n"
				+ "    median_cte.median_11c\n"
				+ "FROM \n"
				+ "    cte\n"
				+ "INNER JOIN \n"
				+ "    cte1 ON cte.COMM_CODE = cte1.COMM_CODE\n"
				+ "CROSS JOIN \n"
				+ "    median_cte\n"
				+ "ORDER BY \n"
				+ "    total_score DESC;\n"
				+ "";
		return queryGst14aa;
	}
	// ********************************************************************************************************************************
	public String QueryFor_gst11d_ZoneWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (  SELECT zc.ZONE_NAME,zc.ZONE_CODE,\n" +
				"SUM(11a.DEPARTMENT_CLOSING_NO + 11a.TAXPAYER_CLOSING_NO) AS col10A,\n" +
				"SUM(11a.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11a.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12A\n" +
				"FROM mis_dla_gst_lgl_1a AS 11a\n" +
				"LEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n" +
				"WHERE 11a.MM_YYYY = '"+month_date+"' AND FORUM_CODE = 7\n" +
				"GROUP BY zc.ZONE_CODE),\n" +
				"cte1 AS (\n" +
				"SELECT zc.ZONE_NAME,zc.ZONE_CODE,\n" +
				"SUM(11b.DEPARTMENT_CLOSING_NO + 11b.TAXPAYER_CLOSING_NO) AS col10B,\n" +
				"SUM(11b.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11b.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12B\n" +
				"FROM mis_dla_gst_lgl_1b AS 11b\n" +
				"LEFT JOIN mis_gst_commcode AS cc ON 11b.COMM_CODE = cc.COMM_CODE\n" +
				"LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE\n" +
				"WHERE\n" +
				"11b.MM_YYYY = '"+month_date+"' AND FORUM_CODE = 7\n" +
				"GROUP BY zc.ZONE_CODE)\n" +
				"SELECT cte.ZONE_NAME,cte.ZONE_CODE,cte.col10A,cte.col12A,cte1.col10B,cte1.col12B,\n" +
				"(cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B) AS numerator,\n" +
				"(cte.col10A + cte1.col10B) AS denominator,\n" +
				"(((cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B)) / (cte.col10A + cte1.col10B)) AS total_score\n" +
				"FROM cte\n" +
				"INNER JOIN cte1 ON cte.ZONE_CODE = cte1.ZONE_CODE\n" +
				"order by total_score asc ;";
		return queryGst14aa;
	}
	public String QueryFor_gst11d_CommissonaryWise(String month_date, String zone_code){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa="WITH cte AS (\n" +
				"\tSELECT zc.ZONE_NAME, zc.ZONE_CODE, cc.COMM_NAME,cc.COMM_CODE, \n" +
				"\t(11a.DEPARTMENT_CLOSING_NO + 11a.TAXPAYER_CLOSING_NO) AS col10A, \n" +
				"\t(11a.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11a.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12A \n" +
				"\tFROM mis_dla_gst_lgl_1a AS 11a \n" +
				"\tLEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE \n" +
				"\tLEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE \n" +
				"\tWHERE 11a.MM_YYYY = '"+month_date+"' AND FORUM_CODE = 7 and zc.ZONE_CODE = '" + zone_code + "'\n" +
				"), \n" +
				"cte1 AS ( \n" +
				"         SELECT zc.ZONE_NAME, zc.ZONE_CODE, cc.COMM_NAME,cc.COMM_CODE, \n" +
				"         (11b.DEPARTMENT_CLOSING_NO + 11b.TAXPAYER_CLOSING_NO) AS col10B, \n" +
				"\t\t(11b.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11b.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12B \n" +
				"          FROM mis_dla_gst_lgl_1b AS 11b \n" +
				"          LEFT JOIN mis_gst_commcode AS cc ON 11b.COMM_CODE = cc.COMM_CODE \n" +
				"          LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE \n" +
				"          WHERE 11b.MM_YYYY = '"+month_date+"' AND FORUM_CODE = 7 and zc.ZONE_CODE = '" + zone_code + "'\n" +
				") \n" +
				"     SELECT cte.ZONE_NAME, cte.ZONE_CODE, cte.COMM_NAME,cte.COMM_CODE, cte.col10A, cte.col12A, cte1.col10B, cte1.col12B, \n" +
				"     (cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B) AS numerator, \n" +
				"      (cte.col10A + cte1.col10B) AS denominator, \n" +
				"\t(((cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B)) / (cte.col10A + cte1.col10B)) AS total_score \n" +
				"FROM cte INNER JOIN cte1 ON cte.COMM_CODE = cte1.COMM_CODE\n" +
				"order by total_score ;";
		return queryGst14aa;
	}
	public String QueryFor_gst11d_AllCommissonaryWise(String month_date){
		//              '" + month_date + "'	 '" + prev_month_new + "'	'" + zone_code + "'		'" + come_name + "' 	'" + next_month_new + "'
		String prev_month_new = DateCalculate.getPreviousMonth(month_date);
		String queryGst14aa= "WITH cte AS (\n" +
				"\tSELECT zc.ZONE_NAME, zc.ZONE_CODE, cc.COMM_NAME,cc.COMM_CODE, \n" +
				"\t(11a.DEPARTMENT_CLOSING_NO + 11a.TAXPAYER_CLOSING_NO) AS col10A, \n" +
				"\t(11a.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11a.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12A \n" +
				"\tFROM mis_dla_gst_lgl_1a AS 11a \n" +
				"\tLEFT JOIN mis_gst_commcode AS cc ON 11a.COMM_CODE = cc.COMM_CODE \n" +
				"\tLEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE \n" +
				"\tWHERE 11a.MM_YYYY = '"+month_date+"' AND FORUM_CODE = 7 \n" +
				"), \n" +
				"cte1 AS ( \n" +
				"         SELECT zc.ZONE_NAME, zc.ZONE_CODE, cc.COMM_NAME,cc.COMM_CODE, \n" +
				"         (11b.DEPARTMENT_CLOSING_NO + 11b.TAXPAYER_CLOSING_NO) AS col10B, \n" +
				"\t\t(11b.DEPARTMENT_BREAKUP_LESS_1YEAR_NO + 11b.TAXPAYER_BREAKUP_LESS_1YEAR_NO) AS col12B \n" +
				"          FROM mis_dla_gst_lgl_1b AS 11b \n" +
				"          LEFT JOIN mis_gst_commcode AS cc ON 11b.COMM_CODE = cc.COMM_CODE \n" +
				"          LEFT JOIN mis_gst_zonecode AS zc ON cc.ZONE_CODE = zc.ZONE_CODE \n" +
				"          WHERE 11b.MM_YYYY = '"+month_date+"' AND FORUM_CODE = 7 \n" +
				") \n" +
				"     SELECT cte.ZONE_NAME, cte.ZONE_CODE, cte.COMM_NAME,cte.COMM_CODE, cte.col10A, cte.col12A, cte1.col10B, cte1.col12B, \n" +
				"     (cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B) AS numerator, \n" +
				"      (cte.col10A + cte1.col10B) AS denominator, \n" +
				"\t(((cte.col10A - cte.col12A) + (cte1.col10B - cte1.col12B)) / (cte.col10A + cte1.col10B)) AS total_score \n" +
				"FROM cte INNER JOIN cte1 ON cte.COMM_CODE = cte1.COMM_CODE\n" +
				"order by total_score ;";

		return queryGst14aa;
	}
}
