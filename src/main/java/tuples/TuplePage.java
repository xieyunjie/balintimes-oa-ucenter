package tuples;

public class TuplePage<l, i>
{
	public final l objectList;
	public final i objectTotalCount;

	public TuplePage(l list, i totalCount)
	{
		this.objectList = list;
		this.objectTotalCount = totalCount;

	}
}
