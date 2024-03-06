package holdemevaluator;

import javax.swing.JOptionPane;

public class LiscenseAgreement {

	/*-  ******************************************************************************
	*  @author PEAK_ 
	* ****************************************************************************** */

	String softwareApplicationAgreement = new StringBuilder().append("SOFTWARE APPLICATION AGREEMENT\n\n")
			.append("Please read this Software Application Agreement (the \"Agreement\") carefully before installing ")
			.append("and using the software application (the \"Software\"). ")
			.append("This Agreement constitutes a legally binding agreement between you (the \"End User\") and the ")
			.append("software developer (the \"Developer\") for the use of the Software.\n\n")
			.append("1. Grant of License. Developer grants to End User a non-exclusive, non-transferable, limited license ")
			.append("to use the Software for personal or commercial use, subject to the terms and conditions ")
			.append("of this Agreement.\n\n")
			.append("2. Ownership. End User acknowledges that the Software, including but not limited to any images, ")
			.append("photographs, animations, video, audio, music, and text incorporated into the Software, ")
			.append("nd any copies of the Software, are owned by Developer and are protected by copyright laws ")
			.append("and international copyright treaties, as well as other intellectual property laws and treaties.\n\n")
			.append("3. Restrictions. End User shall not, and shall not allow any third party to: (a) copy, modify, ")
			.append("adapt, translate, or create derivative works based on the Software; (b) reverse engineer, ")
			.append("decompile, disassemble, or otherwise attempt to discover the source code of the ")
			.append("Software; (c) distribute, sublicense, rent, lease, loan, or otherwise transfer the Software ")
			.append("to any third party; or (d) use the Software for any unlawful purpose or in any manner that ")
			.append("could damage, disable, overburden, or impair the Software.\n\n")
			.append("4. Termination. This Agreement shall automatically terminate if End User breaches any of the ")
			.append("terms and conditions of this Agreement. Upon termination, End User shall immediately ")
			.append("cease all use of the Software and shall destroy all copies of the Software in its possession")
			.append("or control.\n\n")
			.append("5. Disclaimer of Warranties. THE SOFTWARE IS PROVIDED \"AS IS\" WITHOUT WARRANTY ")
			.append("OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, ")
			.append("THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. ")
			.append("DEVELOPER DOES NOT WARRANT THAT THE SOFTWARE WILL MEET END USER'S REQUIREMENTS")
			.append(" OR THAT THE OPERATION OF THE SOFTWARE WILL BE UNINTERRUPTED OR ERROR-FREE. \n\n")
			.append("6. Limitation of Liability. IN NO EVENT SHALL DEVELOPER BE LIABLE TO END USER OR ANY")
			.append("THIRD PARTY FOR ANY INDIRECT, INCIDENTAL, CONSEQUENTIAL, SPECIAL, OR PUNITIVE")
			.append("DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF OR RELATED TO THIS AGREEMENT ")
			.append("OR THE USE OF THE SOFTWARE, EVEN IF DEVELOPER HAS BEEN ADVISED OF THE POSSIBILIT")
			.append("Y OF SUCH DAMAGES.\n\n")
			.append("7. Governing Law. This Agreement shall be governed by and construed in accordance with the")
			.append("laws of the jurisdiction in which Developer is located, without regard to its conflict of ")
			.append("laws principles.\n\n")
			.append("8. Entire Agreement. This Agreement constitutes the entire agreement between ")
			.append("End User and Developer with respect to the use of the Software and supersedes all prior or c")
			.append("ontemporaneous communications and proposals, whether oral or written, ")
			.append("between End User and Developer.\n\n")
			.append("By clicking the \"I Agree\" button, End User acknowledges that End User has read this Agreement, ")
			.append("understands it, and agrees to be bound by its terms and conditions.").toString();

	static void x() {

		final var message = new StringBuilder()
				.append("Please read and agree to the license agreement before using this software.\n\n")
				.append("License Agreement:\n")
				.append("This software is licensed under the GNU General Public License.\n")
				.append("Do you agree to the terms of the license agreement?").toString();

		final int option = JOptionPane.showConfirmDialog(null, message, "License Agreement", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			System.out.println("User agreed to the license agreement.");
			// Continue with the program logic
		} else {
			System.out.println("User did not agree to the license agreement. Exiting the program.");
			System.exit(0);
		}
	}
}
