#include <stdlib.h>
#include "fluid.h"
#include "GL/glut.h"

//http://www.mathfor3dgameprogramming.com/
Fluid::Fluid(long n, long m, float d, float t, float wavespeed, float mu, int tex)
{

	texture = tex;
	width = n; 
	height = m;  
	long count = n * m;  //number of vertices

	buffer[0] = new Vector3D[count];  
	buffer[1] = new Vector3D[count];  
	renderBuffer = 0;  //‰÷»æª∫≥Â«¯

	normal = new Vector3D[count]; //normals
	tangent = new Vector3D[count]; //tangents


	for (int i = 0; i < 3; i++) {
		indices[i] = new int[2 * (n - 1) * (m - 1)];
	}
	for (int i = 0; i < 6; i++) {
		texcoords[i % 3][i / 3] = new float[2 * (n - 1) * (m - 1)];
	}

	// Precompute constants for Equation (15.25).  
	float f1 = wavespeed * wavespeed * t * t / (d * d);
	float f2 = 1.0F / (mu * t + 2);
	k1 = (4.0F - 8.0F * f1) * f2;
	k2 = (mu * t - 2) * f2;
	k3 = 2.0F * f1 * f2;

	// Initialize buffers.  
	long a = 0;

	for (long j = 0; j < m; j++)
	{
		float y = d * j;
		for (long i = 0; i < n; i++)
		{
			if (i == 0 || j == 0 || i == n - 1 || j == m - 1)
				buffer[0][a].Set(d * i, y, 0.0F);
			else {
				int r = rand() % 2;
				if (r == 0)
					buffer[0][a].Set(d * i, y, 1.0F);
				else
					buffer[0][a].Set(d * i, y, 0.0F);
			}
			buffer[1][a] = buffer[0][a];
			normal[a].Set(0.0F, 0.0F, 2.0F * d);
			tangent[a].Set(2.0F * d, 0.0F, 0.0F);
			a++;
		}
	}

	// initialize indices and tex coordinates for loading textures
	a = 0;
	float w = 1.0f / width;
	float h = 1.0f / height;
	for (int i = 0; i < n - 1; i++) {
		for (int j = 0; j < m - 1; j++) {
			long t = i * n + j;
			indices[0][a] = t;
			indices[1][a] = t + 1;
			indices[2][a] = t + n;

			texcoords[0][0][a] = i * w;
			texcoords[0][1][a] = j * h;

			texcoords[1][0][a] = (i + 1) * w;
			texcoords[1][1][a] = j * h;

			texcoords[2][0][a] = i * w;
			texcoords[2][1][a] = (j + 1) * h;

			a++;
		}
	}

	for (int i = 1; i < n; i++) {
		for (int j = 1; j < m; j++) {
			long t = i * n + j;
			indices[0][a] = t;
			indices[1][a] = t - 1;
			indices[2][a] = t - n;

			texcoords[0][0][a] = i * w;
			texcoords[0][1][a] = j * h;

			texcoords[1][0][a] = (i - 1) * w;
			texcoords[1][1][a] = j * h;

			texcoords[2][0][a] = i * w;
			texcoords[2][1][a] = (j - 1) * h;

			a++;
		}
	}

}

Fluid::~Fluid()
{
	delete[] tangent;
	delete[] normal;
	delete[] buffer[1];
	delete[] buffer[0];
}

void Fluid::Evaluate(){
	// Apply Equation (15.25).  
	for (long j = 1; j < height - 1; j++)
	{
		//current vertex movements
		const Vector3D* crnt = buffer[renderBuffer] + j * width;
		//vertexments move for the previous vertex
		Vector3D* prev = buffer[1 - renderBuffer] + j * width;

		// z(i,j,k+1) = k1 * z(i,j,k) + k2 * z(i,j,k-1) +
		//     k3 * (z(i+1,j,k) + z(i-1,j,k) + z(i,j+1,k) + z(i,j-1,k)

		for (long i = 1; i < width - 1; i++)
		{
			prev[i].z = k1 * crnt[i].z + k2 * prev[i].z +
				k3 * (crnt[i + 1].z + crnt[i - 1].z +
					crnt[i + width].z + crnt[i - width].z);
		}
	}

	// Swap buffers.
	renderBuffer = 1 - renderBuffer;


}

void Fluid::Display(){

	glPushMatrix();
	glColor4f(1.0f, 1.0f, 1.0f, 1.f);
	glTranslated(0, -100, 300);
	glRotated(-90, 1, 0, 0);
	glPushAttrib(GL_ALL_ATTRIB_BITS);              // Set the material properties of the waters surface
	glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, texture);  //
	
	for (int k = 0; k < 2; k++) {
		for (int j = -5; j < 3; j++) {
			glBegin(GL_TRIANGLES);
			for (int i = 0; i < 2 * (height - 1) * (width - 1); i++) {
				glTexCoord2f(texcoords[0][0][i], texcoords[0][1][i]);
				glVertex3f(buffer[renderBuffer][indices[0][i]].x + 100 * j,
					buffer[renderBuffer][indices[0][i]].y + 100 * k,
					buffer[renderBuffer][indices[0][i]].z);
				glTexCoord2f(texcoords[1][0][i], texcoords[1][1][i]);
				glVertex3f(buffer[renderBuffer][indices[1][i]].x + 100 * j,
					buffer[renderBuffer][indices[1][i]].y + 100 * k,
					buffer[renderBuffer][indices[1][i]].z);
				glTexCoord2f(texcoords[2][0][i], texcoords[2][1][i]);
				glVertex3f(buffer[renderBuffer][indices[2][i]].x + 100 * j,
					buffer[renderBuffer][indices[2][i]].y + 100 * k,
					buffer[renderBuffer][indices[2][i]].z);
			}
			glEnd();
		}
	}
	
	glDisable(GL_TEXTURE_2D);
	glPopAttrib();
	glPopMatrix();
}

void Fluid::Update(const double& deltaTime)
{
	cnt++;
	if (cnt > 10) {
		cnt = 0;
		Evaluate();
	}
	Display();
	
}
