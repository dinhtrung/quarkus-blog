import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BlogPostService } from 'app/entities/blog-post/blog-post.service';
import { IBlogPost, BlogPost } from 'app/shared/model/blog-post.model';

describe('Service Tests', () => {
  describe('BlogPost Service', () => {
    let injector: TestBed;
    let service: BlogPostService;
    let httpMock: HttpTestingController;
    let elemDefault: IBlogPost;
    let expectedResult: IBlogPost | IBlogPost[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BlogPostService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BlogPost(
        'ID',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            publishedAt: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BlogPost', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            publishedAt: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            publishedAt: currentDate,
            createdAt: currentDate,
            lastModifiedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new BlogPost()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BlogPost', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            summary: 'BBBBBB',
            figure: 'BBBBBB',
            content: 'BBBBBB',
            category: 'BBBBBB',
            slug: 'BBBBBB',
            state: 1,
            weight: 1,
            publishedAt: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastModifiedBy: 'BBBBBB',
            tags: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            publishedAt: currentDate,
            createdAt: currentDate,
            lastModifiedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BlogPost', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            summary: 'BBBBBB',
            figure: 'BBBBBB',
            content: 'BBBBBB',
            category: 'BBBBBB',
            slug: 'BBBBBB',
            state: 1,
            weight: 1,
            publishedAt: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastModifiedBy: 'BBBBBB',
            tags: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            publishedAt: currentDate,
            createdAt: currentDate,
            lastModifiedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BlogPost', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
