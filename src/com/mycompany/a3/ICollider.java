package com.mycompany.a3;

public interface ICollider
{
	public boolean collidesWith(ICollider obj);
	public void handleCollision(ICollider obj);
	public boolean isAlive();
}
