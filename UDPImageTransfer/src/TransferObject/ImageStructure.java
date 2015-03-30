package TransferObject;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class ImageStructure implements Serializable
{

	public ImageStructure(int id, ImageIcon image)
	{
		this.id = id;
		this.image = image;
	}

	private static final long	serialVersionUID	= 1L;
	private int					id;
	private ImageIcon			image;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public ImageIcon getImage()
	{
		return image;
	}

	public void setImage(ImageIcon image)
	{
		this.image = image;
	}

}
