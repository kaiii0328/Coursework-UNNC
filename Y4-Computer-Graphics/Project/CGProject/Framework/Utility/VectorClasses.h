//============================================================================
//
// 2D/3D/4D Vector Classes
//
// Mathematics for 3D Game Programming and Computer Graphics, 3rd ed.
// By Eric Lengyel
//
// The code in this file may be freely used in any software. It is provided
// as-is, with no warranty of any kind.
//
//============================================================================

#include "math.h"

//float InverseSqrt(float n) {
//	const float threehalfs = 1.5F;
//	float y = n;
//
//	long i = *(long*)&y;
//
//	i = 0x5f3759df - (i >> 1);
//	y = *(float*)&i;
//
//	y = y * (threehalfs - ((n * 0.5F) * y * y));
//
//	return y;
//}

class Vector2D
{
public:

	float	x;
	float	y;

	Vector2D() {}

	Vector2D(float r, float s)
	{
		x = r;
		y = s;
	}

	Vector2D& Set(float r, float s)
	{
		x = r;
		y = s;
		return (*this);
	}

	float& operator [](long k)
	{
		return ((&x)[k]);
	}

	const float& operator [](long k) const
	{
		return ((&x)[k]);
	}

	Vector2D& operator +=(const Vector2D& v)
	{
		x += v.x;
		y += v.y;
		return (*this);
	}

	Vector2D& operator -=(const Vector2D& v)
	{
		x -= v.x;
		y -= v.y;
		return (*this);
	}

	Vector2D& operator *=(float t)
	{
		x *= t;
		y *= t;
		return (*this);
	}

	Vector2D& operator /=(float t)
	{
		float f = 1.0F / t;
		x *= f;
		y *= f;
		return (*this);
	}

	Vector2D& operator &=(const Vector2D& v)
	{
		x *= v.x;
		y *= v.y;
		return (*this);
	}

	Vector2D& Normalize(void)
	{
		//return (*this *= InverseSqrt(x * x + y * y));
	}
};


inline Vector2D operator -(const Vector2D& v)
{
	return (Vector2D(-v.x, -v.y));
}

inline Vector2D operator +(const Vector2D& v1, const Vector2D& v2)
{
	return (Vector2D(v1.x + v2.x, v1.y + v2.y));
}

inline Vector2D operator -(const Vector2D& v1, const Vector2D& v2)
{
	return (Vector2D(v1.x - v2.x, v1.y - v2.y));
}

inline Vector2D operator *(const Vector2D& v, float t)
{
	return (Vector2D(v.x * t, v.y * t));
}

inline Vector2D operator *(float t, const Vector2D& v)
{
	return (Vector2D(t * v.x, t * v.y));
}

inline Vector2D operator /(const Vector2D& v, float t)
{
	float f = 1.0F / t;
	return (Vector2D(v.x * f, v.y * f));
}

inline float operator *(const Vector2D& v1, const Vector2D& v2)
{
	return (v1.x * v2.x + v1.y * v2.y);
}

inline Vector2D operator &(const Vector2D& v1, const Vector2D& v2)
{
	return (Vector2D(v1.x * v2.x, v1.y * v2.y));
}

inline bool operator ==(const Vector2D& v1, const Vector2D& v2)
{
	return ((v1.x == v2.x) && (v1.y == v2.y));
}

inline bool operator !=(const Vector2D& v1, const Vector2D& v2)
{
	return ((v1.x != v2.x) || (v1.y != v2.y));
}


class Point2D : public Vector2D
{
public:

	Point2D() {}

	Point2D(float r, float s) : Vector2D(r, s) {}

	Vector2D& GetVector2D(void)
	{
		return (*this);
	}

	const Vector2D& GetVector2D(void) const
	{
		return (*this);
	}

	Point2D& operator =(const Vector2D& v)
	{
		x = v.x;
		y = v.y;
		return (*this);
	}

	Point2D& operator *=(float t)
	{
		x *= t;
		y *= t;
		return (*this);
	}

	Point2D& operator /=(float t)
	{
		float f = 1.0F / t;
		x *= f;
		y *= f;
		return (*this);
	}
};


inline Point2D operator -(const Point2D& p)
{
	return (Point2D(-p.x, -p.y));
}

inline Point2D operator +(const Point2D& p1, const Point2D& p2)
{
	return (Point2D(p1.x + p2.x, p1.y + p2.y));
}

inline Point2D operator +(const Point2D& p, const Vector2D& v)
{
	return (Point2D(p.x + v.x, p.y + v.y));
}

inline Point2D operator -(const Point2D& p, const Vector2D& v)
{
	return (Point2D(p.x - v.x, p.y - v.y));
}

inline Vector2D operator -(const Point2D& p1, const Point2D& p2)
{
	return (Vector2D(p1.x - p2.x, p1.y - p2.y));
}

inline Point2D operator *(const Point2D& p, float t)
{
	return (Point2D(p.x * t, p.y * t));
}

inline Point2D operator *(float t, const Point2D& p)
{
	return (Point2D(t * p.x, t * p.y));
}

inline Point2D operator /(const Point2D& p, float t)
{
	float f = 1.0F / t;
	return (Point2D(p.x * f, p.y * f));
}


inline float Dot(const Vector2D& v1, const Vector2D& v2)
{
	return (v1 * v2);
}

inline Vector2D ProjectOnto(const Vector2D& v1, const Vector2D& v2)
{
	return (v2 * (v1 * v2));
}

inline float Magnitude(const Vector2D& v)
{
	return (sqrt(v.x * v.x + v.y * v.y));
}



inline float InverseMag(const Vector2D& v)
{
	//return (InverseSqrt(v.x * v.x + v.y * v.y));
}



inline float SquaredMag(const Vector2D& v)
{
	return (v.x * v.x + v.y * v.y);
}


class Vector3D
{
public:

	float	x;
	float	y;
	float	z;

	Vector3D() {}

	Vector3D(float r, float s, float t)
	{
		x = r;
		y = s;
		z = t;
	}

	Vector3D(const Vector2D& v)
	{
		x = v.x;
		y = v.y;
		z = 0.0F;
	}

	Vector3D(const Vector2D& v, float u)
	{
		x = v.x;
		y = v.y;
		z = u;
	}

	Vector3D& Set(float r, float s, float t)
	{
		x = r;
		y = s;
		z = t;
		return (*this);
	}

	Vector3D& Set(const Vector2D& v, float u)
	{
		x = v.x;
		y = v.y;
		z = u;
		return (*this);
	}

	float& operator [](long k)
	{
		return ((&x)[k]);
	}

	const float& operator [](long k) const
	{
		return ((&x)[k]);
	}

	Vector2D& GetVector2D(void)
	{
		return (*reinterpret_cast<Vector2D*>(this));
	}

	const Vector2D& GetVector2D(void) const
	{
		return (*reinterpret_cast<const Vector2D*>(this));
	}

	Point2D& GetPoint2D(void)
	{
		return (*reinterpret_cast<Point2D*>(this));
	}

	const Point2D& GetPoint2D(void) const
	{
		return (*reinterpret_cast<const Point2D*>(this));
	}

	Vector3D& operator =(const Vector2D& v)
	{
		x = v.x;
		y = v.y;
		z = 0.0F;
		return (*this);
	}

	Vector3D& operator +=(const Vector3D& v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
		return (*this);
	}

	Vector3D& operator +=(const Vector2D& v)
	{
		x += v.x;
		y += v.y;
		return (*this);
	}

	Vector3D& operator -=(const Vector3D& v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return (*this);
	}

	Vector3D& operator -=(const Vector2D& v)
	{
		x -= v.x;
		y -= v.y;
		return (*this);
	}

	Vector3D& operator *=(float t)
	{
		x *= t;
		y *= t;
		z *= t;
		return (*this);
	}

	Vector3D& operator /=(float t)
	{
		float f = 1.0F / t;
		x *= f;
		y *= f;
		z *= f;
		return (*this);
	}

	Vector3D& operator %=(const Vector3D& v)
	{
		float		r, s;

		r = y * v.z - z * v.y;
		s = z * v.x - x * v.z;
		z = x * v.y - y * v.x;
		x = r;
		y = s;

		return (*this);
	}

	Vector3D& operator &=(const Vector3D& v)
	{
		x *= v.x;
		y *= v.y;
		z *= v.z;
		return (*this);
	}

	Vector3D& Normalize(void)
	{
		//return (*this *= InverseSqrt(x * x + y * y + z * z));
	}
};


inline Vector3D operator -(const Vector3D& v)
{
	return (Vector3D(-v.x, -v.y, -v.z));
}

inline Vector3D operator +(const Vector3D& v1, const Vector3D& v2)
{
	return (Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z));
}

inline Vector3D operator +(const Vector3D& v1, const Vector2D& v2)
{
	return (Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z));
}

inline Vector3D operator -(const Vector3D& v1, const Vector3D& v2)
{
	return (Vector3D(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z));
}

inline Vector3D operator -(const Vector3D& v1, const Vector2D& v2)
{
	return (Vector3D(v1.x - v2.x, v1.y - v2.y, v1.z));
}

inline Vector3D operator *(const Vector3D& v, float t)
{
	return (Vector3D(v.x * t, v.y * t, v.z * t));
}

inline Vector3D operator *(float t, const Vector3D& v)
{
	return (Vector3D(t * v.x, t * v.y, t * v.z));
}

inline Vector3D operator /(const Vector3D& v, float t)
{
	float f = 1.0F / t;
	return (Vector3D(v.x * f, v.y * f, v.z * f));
}

inline float operator *(const Vector3D& v1, const Vector3D& v2)
{
	return (v1.x * v2.x + v1.y * v2.y + v1.z * v2.z);
}

inline float operator *(const Vector3D& v1, const Vector2D& v2)
{
	return (v1.x * v2.x + v1.y * v2.y);
}

inline Vector3D operator %(const Vector3D& v1, const Vector3D& v2)
{
	return (Vector3D(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x));
}

inline Vector3D operator &(const Vector3D& v1, const Vector3D& v2)
{
	return (Vector3D(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z));
}

inline bool operator ==(const Vector3D& v1, const Vector3D& v2)
{
	return ((v1.x == v2.x) && (v1.y == v2.y) && (v1.z == v2.z));
}

inline bool operator !=(const Vector3D& v1, const Vector3D& v2)
{
	return ((v1.x != v2.x) || (v1.y != v2.y) || (v1.z != v2.z));
}


class Point3D : public Vector3D
{
public:

	Point3D() {}

	Point3D(float r, float s, float t) : Vector3D(r, s, t) {}
	Point3D(const Vector2D& v) : Vector3D(v) {}
	Point3D(const Vector2D& v, float u) : Vector3D(v, u) {}

	Vector3D& GetVector3D(void)
	{
		return (*this);
	}

	const Vector3D& GetVector3D(void) const
	{
		return (*this);
	}

	Point2D& GetPoint2D(void)
	{
		return (*reinterpret_cast<Point2D*>(this));
	}

	const Point2D& GetPoint2D(void) const
	{
		return (*reinterpret_cast<const Point2D*>(this));
	}

	Point3D& operator =(const Vector3D& v)
	{
		x = v.x;
		y = v.y;
		z = v.z;
		return (*this);
	}

	Point3D& operator =(const Vector2D& v)
	{
		x = v.x;
		y = v.y;
		z = 0.0F;
		return (*this);
	}

	Point3D& operator *=(float t)
	{
		x *= t;
		y *= t;
		z *= t;
		return (*this);
	}

	Point3D& operator /=(float t)
	{
		float f = 1.0F / t;
		x *= f;
		y *= f;
		z *= f;
		return (*this);
	}

	Point3D& operator &=(const Vector3D& v)
	{
		x *= v.x;
		y *= v.y;
		z *= v.z;
		return (*this);
	}
};


inline Point3D operator -(const Point3D& p)
{
	return (Point3D(-p.x, -p.y, -p.z));
}

inline Point3D operator +(const Point3D& p1, const Point3D& p2)
{
	return (Point3D(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z));
}

inline Point3D operator +(const Point3D& p, const Vector3D& v)
{
	return (Point3D(p.x + v.x, p.y + v.y, p.z + v.z));
}

inline Point3D operator +(const Vector3D& v, const Point3D& p)
{
	return (Point3D(v.x + p.x, v.y + p.y, v.z + p.z));
}

inline Vector3D operator -(const Point3D& p1, const Point3D& p2)
{
	return (Vector3D(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z));
}

inline Point3D operator -(const Point3D& p, const Vector3D& v)
{
	return (Point3D(p.x - v.x, p.y - v.y, p.z - v.z));
}

inline Point3D operator -(const Vector3D& v, const Point3D& p)
{
	return (Point3D(v.x - p.x, v.y - p.y, v.z - p.z));
}

inline Point3D operator *(const Point3D& p, float t)
{
	return (Point3D(p.x * t, p.y * t, p.z * t));
}

inline Point3D operator *(float t, const Point3D& p)
{
	return (Point3D(t * p.x, t * p.y, t * p.z));
}

inline Point3D operator /(const Point3D& p, float t)
{
	float f = 1.0F / t;
	return (Point3D(p.x * f, p.y * f, p.z * f));
}

inline float operator *(const Point3D& p1, const Point3D& p2)
{
	return (p1.x * p2.x + p1.y * p2.y + p1.z * p2.z);
}

inline float operator *(const Point3D& p, const Vector3D& v)
{
	return (p.x * v.x + p.y * v.y + p.z * v.z);
}

inline float operator *(const Vector3D& v, const Point3D& p)
{
	return (v.x * p.x + v.y * p.y + v.z * p.z);
}

inline float operator *(const Point3D& p, const Vector2D& v)
{
	return (p.x * v.x + p.y * v.y);
}

inline float operator *(const Vector2D& v, const Point3D& p)
{
	return (v.x * p.x + v.y * p.y);
}

inline Vector3D operator %(const Point3D& p1, const Point3D& p2)
{
	return (Vector3D(p1.y * p2.z - p1.z * p2.y, p1.z * p2.x - p1.x * p2.z, p1.x * p2.y - p1.y * p2.x));
}

inline Vector3D operator %(const Point3D& p, const Vector3D& v)
{
	return (Vector3D(p.y * v.z - p.z * v.y, p.z * v.x - p.x * v.z, p.x * v.y - p.y * v.x));
}

inline Vector3D operator %(const Vector3D& v, const Point3D& p)
{
	return (Vector3D(v.y * p.z - v.z * p.y, v.z * p.x - v.x * p.z, v.x * p.y - v.y * p.x));
}

inline Point3D operator &(const Point3D& p1, const Point3D& p2)
{
	return (Point3D(p1.x * p2.x, p1.y * p2.y, p1.z * p2.z));
}

inline Point3D operator &(const Point3D& p, const Vector3D& v)
{
	return (Point3D(p.x * v.x, p.y * v.y, p.z * v.z));
}

inline Point3D operator &(const Vector3D& v, const Point3D& p)
{
	return (Point3D(v.x * p.x, v.y * p.y, v.z * p.z));
}


inline float Dot(const Vector3D& v1, const Vector3D& v2)
{
	return (v1 * v2);
}

inline float Dot(const Point3D& p, const Vector3D& v)
{
	return (p * v);
}

inline Vector3D Cross(const Vector3D& v1, const Vector3D& v2)
{
	return (v1 % v2);
}

inline Vector3D Cross(const Point3D& p, const Vector3D& v)
{
	return (p % v);
}

inline Vector3D ProjectOnto(const Vector3D& v1, const Vector3D& v2)
{
	return (v2 * (v1 * v2));
}

inline float Magnitude(const Vector3D& v)
{
	return (sqrt(v.x * v.x + v.y * v.y + v.z * v.z));
}

inline float InverseMag(const Vector3D& v)
{
	//return (InverseSqrt(v.x * v.x + v.y * v.y + v.z * v.z));
}

inline float SquaredMag(const Vector3D& v)
{
	return (v.x * v.x + v.y * v.y + v.z * v.z);
}
