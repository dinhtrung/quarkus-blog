import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBlogPost } from 'app/shared/model/blog-post.model';

type EntityResponseType = HttpResponse<IBlogPost>;
type EntityArrayResponseType = HttpResponse<IBlogPost[]>;

@Injectable({ providedIn: 'root' })
export class BlogPostService {
  public resourceUrl = SERVER_API_URL + 'api/blog-posts';

  constructor(protected http: HttpClient) {}

  create(blogPost: IBlogPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blogPost);
    return this.http
      .post<IBlogPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(blogPost: IBlogPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blogPost);
    return this.http
      .put<IBlogPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IBlogPost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlogPost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(blogPost: IBlogPost): IBlogPost {
    const copy: IBlogPost = Object.assign({}, blogPost, {
      publishedAt: blogPost.publishedAt && blogPost.publishedAt.isValid() ? blogPost.publishedAt.format(DATE_FORMAT) : undefined,
      createdAt: blogPost.createdAt && blogPost.createdAt.isValid() ? blogPost.createdAt.toJSON() : undefined,
      lastModifiedAt: blogPost.lastModifiedAt && blogPost.lastModifiedAt.isValid() ? blogPost.lastModifiedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.publishedAt = res.body.publishedAt ? moment(res.body.publishedAt) : undefined;
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.lastModifiedAt = res.body.lastModifiedAt ? moment(res.body.lastModifiedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((blogPost: IBlogPost) => {
        blogPost.publishedAt = blogPost.publishedAt ? moment(blogPost.publishedAt) : undefined;
        blogPost.createdAt = blogPost.createdAt ? moment(blogPost.createdAt) : undefined;
        blogPost.lastModifiedAt = blogPost.lastModifiedAt ? moment(blogPost.lastModifiedAt) : undefined;
      });
    }
    return res;
  }
}
