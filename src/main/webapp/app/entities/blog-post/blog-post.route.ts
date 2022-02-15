import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBlogPost, BlogPost } from 'app/shared/model/blog-post.model';
import { BlogPostService } from './blog-post.service';
import { BlogPostComponent } from './blog-post.component';
import { BlogPostDetailComponent } from './blog-post-detail.component';
import { BlogPostUpdateComponent } from './blog-post-update.component';

@Injectable({ providedIn: 'root' })
export class BlogPostResolve implements Resolve<IBlogPost> {
  constructor(private service: BlogPostService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBlogPost> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((blogPost: HttpResponse<BlogPost>) => {
          if (blogPost.body) {
            return of(blogPost.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BlogPost());
  }
}

export const blogPostRoute: Routes = [
  {
    path: '',
    component: BlogPostComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sampleApp.blogPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BlogPostDetailComponent,
    resolve: {
      blogPost: BlogPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sampleApp.blogPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BlogPostUpdateComponent,
    resolve: {
      blogPost: BlogPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sampleApp.blogPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BlogPostUpdateComponent,
    resolve: {
      blogPost: BlogPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sampleApp.blogPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
