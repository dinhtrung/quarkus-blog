import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SampleAppTestModule } from '../../../test.module';
import { BlogPostDetailComponent } from 'app/entities/blog-post/blog-post-detail.component';
import { BlogPost } from 'app/shared/model/blog-post.model';

describe('Component Tests', () => {
  describe('BlogPost Management Detail Component', () => {
    let comp: BlogPostDetailComponent;
    let fixture: ComponentFixture<BlogPostDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ blogPost: new BlogPost('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SampleAppTestModule],
        declarations: [BlogPostDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BlogPostDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BlogPostDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load blogPost on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.blogPost).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
