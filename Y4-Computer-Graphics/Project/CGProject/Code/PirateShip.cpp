#include <ModelLoader.h>
#include <PirateShip.h>

#define PI 3.1415

Model boatmodel;

void PirateShip::init() {
	boatmodel.load(filepath.c_str());
}


void PirateShip::Display() {
	glPushMatrix();

	glTranslatef(-248, 150, -250);
	glRotatef(40, 0, 1, 0);
	
	// draw the pirate boat
	glPushMatrix();
	glTranslatef(0, -55, 0);
	glTranslatef(120 * (float)sin(Deg2Rad(rot_angle)), -120 * (float)cos(Deg2Rad(rot_angle)), 0);
	glRotatef(90, 0, 1, 0);
	glScalef(15, 20, 20);	
	glRotatef(rot_angle, -1, 0, 0);
	//glDisable(GL_COLOR_MATERIAL);
	glPushAttrib(GL_ALL_ATTRIB_BITS);
	float mat_ambient[] = { 0.6, 0.11, 0.11, 1.0f };
	float mat_diffuse[] = { 0.6, 0.11, 0.11, 1.0f };
	float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float mat_shininess[] = { 68.0 }; // 0-128

	glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);
	glColor3f(0.6, 0.11, 0.11);
	boatmodel.draw();
	//glEnable(GL_COLOR_MATERIAL);
	glPopAttrib();
	glPopMatrix();

	//draw the base
	drawBase();

	glPopMatrix();
}

void PirateShip::drawBase() {
	//front base
	glPushMatrix();
	glColor3f(0.6, 0.0, 0.0);
	glTranslatef(-1, -55, 0);


	//top cylinder
	glPushMatrix();
	glTranslatef(0, 0, -20);
	GLUquadric* axis = gluNewQuadric();
	gluCylinder(axis, 10, 10, 40, 100, 10);
	glPopMatrix();

	// two bars
	glPushMatrix();
	glRotatef(rot_angle, 0, 0, 1);
	glLineWidth(10);
	glBegin(GL_LINES);	
	glVertex3f(0, -8, 15);
	glVertex3f(0, -110, 15);
	glEnd();

	glLineWidth(10);
	glBegin(GL_LINES);
	glVertex3f(0, -8, -15);
	glVertex3f(0, -110, -15);
	glEnd();
	glPopMatrix();

	//framework of the pirate ship
	radius_outside = 10;
	glPushMatrix();
	glTranslatef(radius_outside * (float)cos(Deg2Rad(60.f)), -radius_outside * (float)sin(Deg2Rad(60.f)), frontWheelPos);
	glRotatef(90, 1, 0, 0);
	glRotatef(30, 0, 1, 0);
	GLUquadric* base = gluNewQuadric();
	gluCylinder(base, 3, 3, 200, 100, 10);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(-radius_outside * (float)cos(Deg2Rad(60.f)), -radius_outside * (float)sin(Deg2Rad(60.f)), frontWheelPos);
	glRotatef(90, 1, 0, 0);
	glRotatef(-30, 0, 1, 0);
	gluCylinder(base, 3, 3, 200, 100, 10);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(radius_outside * (float)cos(Deg2Rad(70.f)) - 5, -radius_outside * (float)sin(Deg2Rad(70.f)) - 100, frontWheelPos);
	glScalef(64, 1, 1);
	glutSolidCube(2);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(radius_outside * (float)cos(Deg2Rad(60.f)), -radius_outside * (float)sin(Deg2Rad(60.f)), backWheelPos);
	glRotatef(90, 1, 0, 0);
	glRotatef(30, 0, 1, 0);
	gluCylinder(base, 3, 3, 200, 100, 10);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(-radius_outside * (float)cos(Deg2Rad(60.f)), -radius_outside * (float)sin(Deg2Rad(60.f)), backWheelPos);
	glRotatef(90, 1, 0, 0);
	glRotatef(-30, 0, 1, 0);
	gluCylinder(base, 3, 3, 200, 100, 10);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(radius_outside * (float)cos(Deg2Rad(70.f)) - 5, -radius_outside * (float)sin(Deg2Rad(70.f)) - 100, backWheelPos);
	glScalef(64, 1, 1);
	glutSolidCube(2);
	glPopMatrix();

	glPopMatrix();


}


// restrict the rotation angel to be in the range of [-60, 60]
// accelration is related to the rotated angel
void PirateShip::Update(const double& deltaTime) {
	rot_speed = sqrt(2 * 9.8 * (cos(Deg2Rad(abs(rot_angle)) - 0.86))) /5;

	if (!rev)
		rot_angle += rot_speed;
	else
		rot_angle -= rot_speed;

	if (rot_angle > 60)
		rev = !rev;
	else if (rot_angle < -60)
		rev = !rev;

}

float PirateShip::Deg2Rad(float deg)
{
	return (float)(deg * PI) / 180;
}