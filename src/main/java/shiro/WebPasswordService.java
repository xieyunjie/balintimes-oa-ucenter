package shiro;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.SimpleHash;

public class WebPasswordService implements PasswordService
{
	private String AlgorithmName = "MD5";
	private String Salt = "";

	@Override
	public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException
	{
		return new SimpleHash(AlgorithmName, plaintextPassword, Salt).toString();
	}

	@Override
	public boolean passwordsMatch(Object submittedPlaintext, String encrypted)
	{
		String p = new SimpleHash(AlgorithmName, submittedPlaintext, Salt).toString();

		return p.equalsIgnoreCase(encrypted);
	}

	public String getAlgorithmName()
	{
		return AlgorithmName;
	}

	public void setAlgorithmName(String algorithmName)
	{
		AlgorithmName = algorithmName;
	}

	public String getSalt()
	{
		return Salt;
	}

	public void setSalt(String salt)
	{
		Salt = salt;
	}

}
