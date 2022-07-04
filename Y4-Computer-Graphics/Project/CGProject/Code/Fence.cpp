#include <ModelLoader.h>
#include "Fence.h"

Model fencemodel;

void Fence::Display()
{
	glPushMatrix();
	glPushAttrib(GL_ALL_ATTRIB_BITS);
	float mat_ambient[] = { 0.43, 0.30, 0.21, 1.0f };
	float mat_diffuse[] = { 0.43, 0.30, 0.21, 1.0f };
	float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float mat_shininess[] = { 120.0 }; // 0-128

	glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);

	glColor3f(0.43, 0.30, 0.21);

	// draw front fence
	for (int i = 0; i < 6; i++) {
		if (i == 2 || i == 3)
			continue;

		glPushMatrix();
		glTranslatef(-435 + 125*i, -100, 0);
		glRotated(90, 0, 1, 0);
		glScalef(40, 40, 37);
		fencemodel.draw();
		glPopMatrix();
	}

	// draw fences at two sides
	for (int i = 0; i < 4; i++) {
		glPushMatrix();
		glTranslatef(-490, -100, -60 - 125*i);
		glScalef(40, 38, 37);
		fencemodel.draw();
		glPopMatrix();

		glPushMatrix();
		glTranslatef(260, -100, -60 - 125 * i);
		glScalef(40, 38, 37);
		fencemodel.draw();
		glPopMatrix();
	}

	// draw fences at the back
	for (int i = 0; i < 6; i++) {
		glPushMatrix();
		glTranslatef(-435 + 125 * i, -100, -495);
		glRotated(90, 0, 1, 0);
		glScalef(40, 40, 37);
		fencemodel.draw();
		glPopMatrix();
	}

	glPopAttrib();
	glPopMatrix();
	

}

void Fence::init()
{
	// load fence model
	fencemodel.load(modelpath.c_str());
}
