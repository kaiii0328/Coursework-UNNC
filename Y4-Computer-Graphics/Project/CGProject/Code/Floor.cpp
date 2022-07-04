#include "Floor.h"

void Floor::Display()
{
	// Draws an exciting chequered floor
	glPushMatrix();
	glPushAttrib(GL_ALL_ATTRIB_BITS);
	glEnable(GL_COLOR_MATERIAL);
	
	glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, textureid);  
	for (int i = -5; i < 3; i++)
	{		
		for (int j = -5; j < 5; j++)
		{			
			// leave spaces for the river
			if (j == 1 || j==2) continue;
			
			glBegin(GL_QUADS);
			glColor3f(1.0f, 1.f, 1.0f);
			glNormal3f(0.0f, 1.0f, 0.0f);
			
			// set the coordinates for apply grass textures
			glTexCoord2f(0.0f, 1.0f);
			glVertex3f(scale[0] * static_cast<float>(i) + scale[0], -100.0f, scale[2] * static_cast<float>(j) + scale[2]);
			glTexCoord2f(0.0f, 0.0f);
			glVertex3f(scale[0] * static_cast<float>(i) + scale[0], -100.0f, scale[2] * static_cast<float>(j));
			glTexCoord2f(1.0f, 0.0f);
			glVertex3f(scale[0] * static_cast<float>(i), -100.0f, scale[2] * static_cast<float>(j));
			glTexCoord2f(1.0f, 1.0f);
			glVertex3f(scale[0] * static_cast<float>(i), -100.0f, scale[2] * static_cast<float>(j) + scale[2]);
			glEnd();
		}
	}
	glDisable(GL_TEXTURE_2D);
	glDisable(GL_COLOR_MATERIAL);
	glPopAttrib();
	glPopMatrix();
}