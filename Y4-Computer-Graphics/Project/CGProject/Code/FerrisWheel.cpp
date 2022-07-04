//#include <glew/glew.h>
#include <FerrisWheel.h>


#define PI 3.1415

void FerrisWheel::Display() {
	glPushMatrix();

	glTranslatef(0, 50, -250);

	drawCabins();
	drawBars();
	glPushMatrix();
	glPushAttrib(GL_ALL_ATTRIB_BITS);
	float mat_ambient[] = { 0.6, 0, 0, 1.0f };
	float mat_diffuse[] = { 0.6, 0, 0, 1.0f };
	float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float mat_shininess[] = { 48.0 }; // 0-128

	glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);

	drawWheel();


	drawBase();

	glPopAttrib();
	glPopMatrix();

	glPopMatrix();
}

// draw the front and back bases that supports the wheels
void FerrisWheel::drawBase() {
	//front base
	glPushMatrix();
	glColor3f(0.6, 0.0, 0.0);

	glPushMatrix();
	glTranslatef(radius_outside * (float)cos(PI / 3), -radius_outside * (float)sin(PI / 3), frontWheelPos);
	glRotatef(90, 1, 0, 0);
	glRotatef(30, 0, 1, 0);
	GLUquadric* base = gluNewQuadric();
	gluCylinder(base, 3, 3, 100, 100, 10);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(-radius_outside * (float)cos(PI / 3), -radius_outside * (float)sin(PI / 3), frontWheelPos);
	glRotatef(90, 1, 0, 0);
	glRotatef(-30, 0, 1, 0);
	gluCylinder(base, 3, 3, 100, 100, 10);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(radius_outside * (float)cos(PI / 3) - 30, -radius_outside * (float)sin(PI / 3) - 40, frontWheelPos);
	glScalef(50, 1, 1);
	glutSolidCube(2);
	glPopMatrix();


	// back base
	glPushMatrix();
	glTranslatef(radius_outside * (float)cos(PI / 3), -radius_outside * (float)sin(PI / 3), backWheelPos);
	glRotatef(90, 1, 0, 0);
	glRotatef(30, 0, 1, 0);
	gluCylinder(base, 3, 3, 100, 100, 10);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(-radius_outside * (float)cos(PI / 3), -radius_outside * (float)sin(PI / 3), backWheelPos);
	glRotatef(90, 1, 0, 0);
	glRotatef(-30, 0, 1, 0);
	gluCylinder(base, 3, 3, 100, 100, 10);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(radius_outside * (float)cos(PI / 3) - 30, -radius_outside * (float)sin(PI / 3) - 40, backWheelPos);
	glScalef(50, 1, 1);
	glutSolidCube(2);
	glPopMatrix();

	glPopMatrix();


}


// draw bars that starts from the center axis to the wheels
void FerrisWheel::drawBars() {
	int num_of_bars = 24;

	glPushMatrix();
	glRotatef(rot_angle, 0, 0, 1);
	for (int i = 0; i < num_of_bars; i++) {
		GLfloat radius = (float)(360 / num_of_bars) * i;

		glPushMatrix();
		glColor3f(0.92f, 0.12f, 0.12f);
		glRotatef(radius, 0, 0, 1);
		glRotatef(-10, 1, 0, 0);
		glTranslatef(0, 25, 30);
		glScalef(1, 55, 1);
		glutSolidCube(1);
		glPopMatrix();

		glPushMatrix();
		glRotatef(radius, 0, 0, 1);
		glRotatef(10, 1, 0, 0);
		glTranslatef(0, 25, -30);
		glScalef(1, 55, 1);
		glutSolidCube(1);
		glPopMatrix();
	}
	glPopMatrix();
}

// draw cabins in the ferris wheel
void FerrisWheel::drawCabins() {
	int num_of_cabins = 5;
	GLfloat barRadius = 3;

	glPushMatrix();
	glRotatef(rot_angle, 0, 0, 1);
	for (int i = 0; i < num_of_cabins; i++) {
		GLfloat radius = (float)(360 / num_of_cabins) * i;

		glPushMatrix();
		glTranslatef(radius_inside * (GLfloat)cos(Deg2Rad(radius)), radius_inside * (GLfloat)sin(Deg2Rad(radius)), 0);
		glColor3f(0.9, 0.3, 0.2);
		// draw bars that connects the front and back wheel
		GLfloat barEnd = 14;
		glBegin(GL_TRIANGLE_FAN);
		glVertex3f(0, 0, -barEnd);
		for (float angle = 0; angle <= 360; angle += 0.1f) {
			glVertex3f(barRadius * (GLfloat)cos(Deg2Rad(angle)), barRadius * (GLfloat)sin(Deg2Rad(angle)), -barEnd);
		}
		glEnd();

		glPushMatrix();
		glTranslatef(0, 0, -barEnd);
		GLUquadric* bar = gluNewQuadric();
		GLdouble height = 2 * barEnd;
		gluCylinder(bar, barRadius, barRadius, (GLfloat)height, 100, 10);
		glPopMatrix();

		glBegin(GL_TRIANGLE_FAN);
		glVertex3f(0, 0, barEnd);
		for (float angle = 0; angle <= 360; angle += 0.1f) {
			glVertex3f(barRadius * (GLfloat)cos(Deg2Rad(angle)), barRadius * (GLfloat)sin(Deg2Rad(angle)), barEnd);
		}
		glEnd();

		// draw  cubes, which are the cabins for riders
		glPushMatrix();
		glRotatef(rot_angle, 0, 0, -1);
		glTranslatef(0, -8, 0);
		glutSolidCube(15);
		glPopMatrix();

		glPopMatrix();
	}

	glPopMatrix();
}

//draw the wheels
void FerrisWheel::drawWheel() {
	glPushMatrix();

	glColor3f(0.6f, 0.0f, 0.0f);

	// Center front
	glBegin(GL_TRIANGLE_FAN);
	glVertex3f(0, 0, centerLength / 2);
	for (GLfloat angle = 0; angle <= 360; angle += 0.1f)
	{
		glVertex3f(centerRadius * (float)cos(Deg2Rad(angle)), centerRadius * (float)sin(Deg2Rad(angle)), centerLength / 2);
	}
	glEnd();

	//center
	glPushMatrix();
	glTranslatef(0, 0, -centerLength / 2);
	GLUquadric* center = gluNewQuadric();
	gluCylinder(center, centerRadius, centerRadius, centerLength, 100, 10);
	glPopMatrix();

	// Center back
	glBegin(GL_TRIANGLE_FAN);
	glVertex3f(0, 0, -centerLength / 2);
	for (GLfloat angle = 0; angle <= 360; angle += 0.1f) {
		glVertex3f(centerRadius * (float)cos(Deg2Rad(angle)), centerRadius * (float)sin(Deg2Rad(angle)), -centerLength / 2);
	}
	glEnd();


	glPushMatrix();
	frontWheelPos = 1 * centerLength / 8 + 5.f;
	glTranslatef(0, 0, frontWheelPos);
	GLUquadric* wheelfront = gluNewQuadric();
	gluCylinder(wheelfront, radius_outside, radius_outside, 5, 100, 10);
	gluCylinder(wheelfront, radius_inside, radius_inside, 5, 100, 10);
	for (float angle = 0; angle <= 360; angle += 0.1f) {
		glBegin(GL_POLYGON);
		glVertex3f(radius_outside * (float)cos(Deg2Rad(angle)), radius_outside * (float)sin(Deg2Rad(angle)), 0);
		glVertex3f(radius_inside * (float)cos(Deg2Rad(angle)), radius_inside * (float)sin(Deg2Rad(angle)), 0);
		glVertex3f(radius_inside * (float)cos(Deg2Rad(angle + 0.2f)), radius_inside * (float)sin(Deg2Rad(angle + 0.2f)), 0);
		glVertex3f(radius_outside * (float)cos(Deg2Rad(angle + 0.2f)), radius_outside * (float)sin(Deg2Rad(angle + 0.2f)), 0);
		glEnd();
	}
	glPopMatrix();

	glPushMatrix();
	backWheelPos = -1 * centerLength / 8 - 7.5f;
	glTranslatef(0, 0, backWheelPos);
	GLUquadric* wheelback = gluNewQuadric();
	gluCylinder(wheelback, radius_outside, radius_outside, 5, 100, 10);
	gluCylinder(wheelfront, radius_inside, radius_inside, 5, 100, 10);
	for (float angle = 0; angle <= 360; angle += 0.1f) {
		glBegin(GL_POLYGON);
		glVertex3f(radius_outside * (float)cos(Deg2Rad(angle)), radius_outside * (float)sin(Deg2Rad(angle)), 0);
		glVertex3f(radius_inside * (float)cos(Deg2Rad(angle)), radius_inside * (float)sin(Deg2Rad(angle)), 0);
		glVertex3f(radius_inside * (float)cos(Deg2Rad(angle + 0.2f)), radius_inside * (float)sin(Deg2Rad(angle + 0.2f)), 0);
		glVertex3f(radius_outside * (float)cos(Deg2Rad(angle + 0.2f)), radius_outside * (float)sin(Deg2Rad(angle + 0.2f)), 0);
		glEnd();
	}
	glPopMatrix();
	glPopMatrix();
}


//convert degrees to radius
float FerrisWheel::Deg2Rad(float deg)
{
	return (float)(deg * PI) / 180;
}

// change the rotation speed by keyboard inputs
void FerrisWheel::HandleKey(unsigned char key, int state, int x, int y) {
	switch (key)
	{
	case 'R':
	case 'r':
		rot_speed += 0.5f;
		break;

	case 'x':
	case 'X':
		rot_speed -= 0.5f;
		break;
	default:
		break;
	}
}

void FerrisWheel::Update(const double& deltaTime) {
	rot_angle += rot_speed;
}