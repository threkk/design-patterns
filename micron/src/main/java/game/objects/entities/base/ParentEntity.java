package game.objects.entities.base;

import game.objects.Killable;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.data.structures.base.Container;

/*****************************************************************************
 * A ParentEntity is a KeyPointEntity that has "child" entities that act as 
 * sub-components of it. They are "attached" to individual keypoints.
 * 
 * A good example is an MatrixEntity that acts as a turret for its parent. It 
 * moves and rotates with the ParentEntity, but independently rotates towards and 
 * fires at its target.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class ParentEntity extends KeyPointEntity implements 
		Container<MatrixEntity>
{
	/**
	 * The list of EntityLinks used to manage children.
	 **/
	protected LinkedHashMap<MatrixEntity, EntityLink> links;
	
	/**
	 * Whether or not to render children above or below the parent.
	 **/
	protected boolean childrenOnTop;

	/*************************************************************************
	 * Creates a ParentEntity without any children.
	 *************************************************************************/
	public ParentEntity()
	{
		childrenOnTop = true;
		links = new LinkedHashMap<MatrixEntity, EntityLink>();
	}

	/*************************************************************************
	 * Learns whether all children are being rendered on top of this Entity.
	 * 
	 * @return True if the children are visible "above" this parent, false
	 *         otherwise.
	 *************************************************************************/
	public boolean childrenOnTop() { return childrenOnTop; }

	/*************************************************************************
	 * Redefines whether the parent or the children should be rendered on top.
	 * 
	 * @param childrenOnTop
	 * 			  Whether or not to render children above the parent. 
	 *************************************************************************/
	public void setChildrenOnTop(boolean childrenOnTop)
	{
		this.childrenOnTop = childrenOnTop;
	}

	/*************************************************************************
	 * Attempts to add the indicated child Entity.
	 * 
	 * @param child
	 * 			  The Entity to add.
	 * 
	 * @return Whether or not the add was successful.
	 ***************************************************************/ @Override
	public boolean add(MatrixEntity child)
	{
		return add(child, 0, true);
	}

	/*************************************************************************
	 * Attempts to add the indicated child Entity, while keeping in mind how to
	 * affect the child's rotation.
	 * 
	 * @param child
	 * 			  The Entity to add.
	 * @param point
	 * 			  The index of the keypoint to use.
	 * @param constrainRotation
	 * 			  Whether or not to make the child rotate along with its parent.
	 * 
	 * @return Whether or not the add was successful.
	 *************************************************************************/
	public boolean add(MatrixEntity child, int point, boolean constrainRotation)
	{
		if(links.containsKey(child))
			return false;
		links.put(child, new EntityLink(child, point, constrainRotation));
		return true;
	}

	/*************************************************************************
	 * Attempts to add the indicated children.
	 * 
	 * @param children
	 * 			  The list of child Entities to add.
	 ***************************************************************/ @Override
	public void add(MatrixEntity... children)
	{
		for(MatrixEntity child : children)
			add(child);
	}

	/*************************************************************************
	 * Attempts to remove the indicated child.
	 * 
	 * @param child
	 * 			  The child to remove.
	 * 
	 * @return True if the remove succeeded, false if it didn't (i.e., if the
	 *         child was not in the list).
	 ***************************************************************/ @Override
	public boolean remove(MatrixEntity child)
	{
		return links.remove(child) != null;
	}

	/*************************************************************************
	 * Removes the indicated children.
	 * 
	 * @param children
	 * 			  The child Entities to remove.
	 ***************************************************************/ @Override
	public void remove(MatrixEntity... children)
	{
		for(MatrixEntity child : children)
			remove(child);
	}

	/*************************************************************************
	 * Determines if the ParentEntity contains the indicated child.
	 * 
	 * @param child
	 * 			  The Entity to check for.
	 * 
	 * @return True if the Entity is managed by the parent, false otherwise.
	 ***************************************************************/ @Override
	public boolean contains(MatrixEntity child)
	{
		return links.containsKey(child);
	}

	/*************************************************************************
	 * Learns the size of the ParentEntity.
	 * 
	 * @return The number of children managed.
	 ***************************************************************/ @Override
	public int size()
	{
		return links.size();
	}

	/*************************************************************************
	 * Clears the ParentEntity of all children.
	 ***************************************************************/ @Override
	public void clear()
	{
		links.clear();
	}

	/*************************************************************************
	 * Kills all children on death.
	 ***************************************************************/ @Override
	protected void onDeath()
	{
		super.onDeath();
		for(EntityLink link : links.values())
			link.kill();
	}

	/*************************************************************************
	 * Updates the parent Entity and all children.
	 * 
	 * @param delta
	 * 			  The amount of time that has passed since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		super.update(delta);
		for(EntityLink link : links.values())
			link.update(delta);
	}

	/*************************************************************************
	 * Renders the children and parent Entities.
	 ***************************************************************/ @Override
	public void render()
	{
		if(childrenOnTop)
		{
			super.render();
			for(MatrixEntity child : this)
				child.render();
		}
		else
		{
			for(MatrixEntity child : this)
				child.render();
			super.render();
		}
	}

	/*************************************************************************
	 * Allows access to the list of child entities.
	 * 
	 * @return An iterator through the list of child Entities.
	 ***************************************************************/ @Override
	public Iterator<MatrixEntity> iterator()
	{
		return new EntityIterator(links.values());
	}

	/*************************************************************************
	 * An iterator through the list of child Entities.
	 *************************************************************************/
	private class EntityIterator implements Iterator<MatrixEntity>
	{
		/**
		 * The iterator through the list of EntityLinks used to 
		 **/
		protected Iterator<EntityLink> iterator;

		/*********************************************************************
		 * Creates the EntityIterator.
		 * 
		 * @param iterable
		 *            The iterator through EntityLinks to use for iteration.
		 *********************************************************************/
		public EntityIterator(Iterable<EntityLink> iterable)
		{
			this.iterator = iterable.iterator();
		}

		/*********************************************************************
		 * Determines if iteration should advance.
		 * 
		 * @return Whether or not the iterator still has more entries to iterate
		 *         through.
		 ***********************************************************/ @Override
		public boolean hasNext()
		{
			return iterator.hasNext();
		}

		/*********************************************************************
		 * Advances iteration.
		 * 
		 * @return The next Entity in the list.
		 ***********************************************************/ @Override
		public MatrixEntity next()
		{
			EntityLink link = iterator.next();
			return link == null ? null : link.child;
		}

		/*********************************************************************
		 * Delegates removing to the internal iterator.
		 ***********************************************************/ @Override
		public void remove()
		{
			iterator.remove();
		}
	}

	/*************************************************************************
	 * An EntityLink controls ensuring a child Entity's location, rotation, and
	 * manages updating it.
	 *************************************************************************/
	protected class EntityLink implements Updateable, Killable
	{
		/**
		 * The child Entity.
		 **/
		protected MatrixEntity child;
		
		/**
		 * The index of the keypoint at which to locate the child Entity.
		 **/
		protected int linkingPoint;
		
		/**
		 * Whether or not the EntityLink should attempt to rotate the child
		 * with the parent.
		 **/
		protected boolean linkRotation;
		
		/**
		 * Used to control rotation, if necessary.
		 **/
		protected float lastRotation;

		/*********************************************************************
		 * Creates an EntityLink that will manage the indicated child.
		 * 
		 * @param child
		 * 			  The child Entity to manage.
		 * @param linkRotation
		 * 			  Whether or not to affect the child Entity's rotation.
		 *********************************************************************/
		public EntityLink(MatrixEntity child, int linkingPoint, boolean linkRotation)
		{
			this.child = child;
			this.linkingPoint = linkingPoint;
			this.linkRotation = linkRotation;
			adjustChild();
		}

		/*********************************************************************
		 * Updates the child and manages its location and rotation (if 
		 * necessary).
		 * 
		 * @param delta
		 * 			  The amount of time that has passed since the last update.
		 ***********************************************************/ @Override
		public void update(int delta)
		{
			if(child != null)
			{
				child.update(delta);
				adjustChild();
			}
		}
		
		/*********************************************************************
		 * Adjusts the child Entity's location and rotation, as necessary.
		 *********************************************************************/
		public void adjustChild()
		{
			child.location().set(pointAt(linkingPoint));
			if(linkRotation)
				child.rotation().rotate(rotation.degrees() - lastRotation);
			lastRotation = rotation.degrees();
		}

		/*********************************************************************
		 * Kills the child.
		 ***********************************************************/ @Override
		public void kill()
		{
			if(child != null)
			{
				child.setMatrix(matrix);
				child.kill();
				child = null;
			}
		}
	}
}