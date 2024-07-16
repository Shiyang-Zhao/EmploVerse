// import { NextResponse } from 'next/server';
// import type { NextRequest } from 'next/server';

// export function middleware(request: NextRequest) {
//     const token = request.cookies.get('jwt'); // Adjust based on how you store your auth token

//     const url = request.nextUrl.clone();
//     const { pathname } = request.nextUrl;

//     const protectedPaths = ['/profile', '/employees'];

//     if (protectedPaths.includes(pathname) && !token) {
//         url.pathname = '/?modal=login';
//         return NextResponse.redirect(url);
//     }

//     return NextResponse.next();
// }
