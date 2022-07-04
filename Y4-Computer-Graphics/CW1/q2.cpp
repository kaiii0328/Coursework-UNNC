#include <GL/glut.h>
#include <math.h>

GLfloat scale = 1;

float angle = 0.0;
int counter = 0;
int time = 0;
int width, height;

GLfloat x1 = 0;
GLfloat z1 = 0;
float animateTime=0;
const double pi = 3.1415926;


void drawBase() {
	GLUquadric* base;
	base = gluNewQuadric();
	glTranslatef(0, 0, -30);
	glTranslated(x1, 0, z1);
	glScalef(scale, scale, scale);
	glRotatef(angle, 0, 1, 0);
	gluSphere(base, 2, 100, 10);
}

void drawBody() {
	glTranslatef(0, 3.5, 0);
	GLUquadric* body;
	body = gluNewQuadric();
	gluSphere(body, 1.5, 100, 10);
}

void drawHead() {
	glPushMatrix();
	glTranslatef(0, 2.5, 0);
	GLUquadric* head;
	head = gluNewQuadric();
	gluSphere(head, 1, 100, 10);

	glPushMatrix();
	glTranslatef(-0.3, 0.3, 0.8);
	glColor3f(0, 0, 0);
	GLUquadric* lefteye;
	lefteye = gluNewQuadric();
	gluSphere(lefteye, 0.15, 100, 10);
	//glBegin(GL_LINE_LOOP);
	//int sides = 40;
	//double radius = 0.15;
	//for (int a = 0; a < 360; a += 360 / sides)
	//{
	//	double heading = a * 3.1415926535897932384626433832795 / 180;
	//	glVertex2d(cos(heading) * radius, sin(heading) * radius);
	//}
	glEnd();
	glPopMatrix();

	glPushMatrix();
	glTranslatef(0.3, 0.3, 0.8);
	glColor3f(0, 0, 0);
	GLUquadric* righteye;
	righteye = gluNewQuadric();
	gluSphere(righteye, 0.15, 100, 10);
	/*glBegin(GL_LINE_LOOP);
	for (int a = 0; a < 360; a += 360 / sides)
	{
		double heading = a * 3.1415926535897932384626433832795 / 180;
		glVertex2d(cos(heading) * radius, sin(heading) * radius);
	}
	glEnd();*/
	glPopMatrix();
}

void drawCube(GLfloat x, GLfloat y, GLfloat z ) {
	glBegin(GL_QUADS);        // Draw The Cube Using quads
	glColor3f(1, 1, 1);
	glVertex3f(x,y,-z);    // Top Right Of The Quad (Top)
	glVertex3f(-x, y, -z);    // Top Left Of The Quad (Top)
	glVertex3f(-x, y, z);    // Bottom Left Of The Quad (Top)
	glVertex3f(x, y, z);    // Bottom Right Of The Quad (Top)
	glVertex3f(x, -y, -z);    // Top Right Of The Quad (Bottom)
	glVertex3f(-x, -y, z);    // Top Left Of The Quad (Bottom)
	glVertex3f(-x, -y, -z);    // Bottom Left Of The Quad (Bottom)
	glVertex3f(x, -y, -z);    // Bottom Right Of The Quad (Bottom)
	glVertex3f(x, y, z);    // Top Right Of The Quad (Front)
	glVertex3f(-x, y, z);    // Top Left Of The Quad (Front)
	glVertex3f(-x, -y, z);    // Bottom Left Of The Quad (Front)
	glVertex3f(x, -y, z);    // Bottom Right Of The Quad (Front)
	glVertex3f(x, -y, -z);    // Top Right Of The Quad (Back)
	glVertex3f(-x, -y, -z);    // Top Left Of The Quad (Back)
	glVertex3f(-x, y, -z);    // Bottom Left Of The Quad (Back)
	glVertex3f(x, y, -z);    // Bottom Right Of The Quad (Back)
	glColor3f(0.4, 0.4, 0.4);
	glVertex3f(-x, y, z);    // Top Right Of The Quad (Left)
	glVertex3f(-x, y, -z);    // Top Left Of The Quad (Left)
	glVertex3f(-x, -y, -z);    // Bottom Left Of The Quad (Left)
	glVertex3f(-x, -y, z);    // Bottom Right Of The Quad (Left)
	glColor3f(0.4, 0.4, 0.4);
	glVertex3f(x, y, -z);    // Top Right Of The Quad (Right)
	glVertex3f(x, y, z);    // Top Left Of The Quad (Right)
	glVertex3f(x, -y, z);    // Bottom Left Of The Quad (Right)
	glVertex3f(x, -y, -z);    // Bottom Right Of The Quad (Right)
	glEnd();            // End Drawing The Cube
}

void drawArms() {
	glPopMatrix();
	glPushMatrix();
	glTranslatef(1.414, 1.414, 0);
	glColor3f(1, 1, 1);
	glRotatef(45, 0, 0, 1);

	drawCube(0.5f,0.1f,0.1f);  //1*0.2*0.2
	glRotatef(-45, 0, 0, 1);
	glTranslatef(0.49, 0.49, 0);
	glRotatef(45, 0, 0, 1);
	drawCube(0.2, 0.4, 0.2);
	glPopMatrix();

	glTranslatef(-1.414, 1.414, 0);
	glColor3f(1, 1, 1);
	glRotatef(-45, 0, 0, 1);

	drawCube(0.5f, 0.1f, 0.1f);  //1*0.2*0.2
	glRotatef(45, 0, 0, 1);
	glTranslatef(-0.49, 0.49, 0);
	glRotatef(-45, 0, 0, 1);
	drawCube(0.2, 0.4, 0.2);
	glPopMatrix();

}

void display() {
	//draw snow man
	glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);
	glEnable(GL_DEPTH_TEST);
	glDepthFunc(GL_LEQUAL);
	glHint(GL_PERSPECTIVE_CORRECTION_HINT,GL_NICEST);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	glColor3d(1,1,1);
	drawBase();
	
	drawBody();
	
	drawHead();
	
	drawArms();
	
	glutSwapBuffers();
}

void init(void) {	

	glClearColor(0,0,0.f,1.f);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(60, 1, 0, 100);
}


void update(const double& deltaTime) {
	// update the time and rotation steps
	animateTime = static_cast<float>(deltaTime);	

	double rounds =  animateTime / 10;
	double rem = rounds - floor(rounds);

	// check if we are in the 0 to 3.
	if (rem <= 0.33f){
		scale = 1 - 1.65 * rem;
		angle = 360 * rem;
		if (scale < 0.5)
			scale = 0.5;
	}
	else if (rem <= 0.66f){
		scale = 0.5 - 0.51 * (rem-0.33);
		angle = 360 * rem;
		if (scale < 0.33)
			scale = 0.33;
	}
	// check if we are in the 6.6 to 10 seconds of animation
	else
	{
		scale = 0.33 + 2.01 * (rem - 0.66);
		angle = 264.7 * rem - 264.7;
		if (scale > 1)
			scale = 1;		
	}

	int radius = 5;
	
	float theta = 2 * pi / 10 * animateTime;
	x1 = -sin(theta) * radius;
	z1 = radius*(1 - cos(theta));
}


void idleFunc() {
	// Get elapsed time (milliseconds)
	int t = glutGet(GLUT_ELAPSED_TIME);
	// Calculate difference in seconds between previous and current call 
	double diff = static_cast<double>(t - time) / 1000.0;
	//int delta = int(diff)%10;
	//time = t;
	// Call Update methods
	update(diff);
	// Signify flag for display/bufferswap
	glutPostRedisplay();
}

void reshapeFunc(int w, int h) {
	int offset = 50;
	int dis = (w > h ? h : w) - offset * 2;

	
	glViewport(offset, offset, (GLsizei)dis, (GLsizei)dis);
	//printf("reshape: w=%d, h=%d, dis=%d\n", w, h, dis);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	glOrtho(-1.5, 1.5, -1.5, 1.5, 0, 10);
	//gluOrtho2D(-1.5, 1.5, -1.5, 1.5);

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}



int main(int argc, char* argv[]) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	glutInitWindowSize(1200, 1200);
	glutInitWindowPosition(0, 0);
	glutCreateWindow("SnowMan");
	init();

	glutDisplayFunc(display);
	glutIdleFunc(idleFunc);
	//glutReshapeFunc(reshapeFunc);
	//glutTimerFunc(10, TimerFunction, 1);
	glutMainLoop();


}