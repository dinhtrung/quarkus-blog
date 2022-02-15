import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IBlogPost, BlogPost } from 'app/shared/model/blog-post.model';
import { BlogPostService } from './blog-post.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-blog-post-update',
  templateUrl: './blog-post-update.component.html',
})
export class BlogPostUpdateComponent implements OnInit {
  isSaving = false;
  publishedAtDp: any;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.maxLength(80)]],
    summary: [],
    figure: [],
    figureContentType: [],
    content: [],
    category: [],
    slug: [null, [Validators.required, Validators.maxLength(255)]],
    state: [null, [Validators.required, Validators.min(0), Validators.max(9)]],
    weight: [],
    publishedAt: [],
    createdAt: [],
    lastModifiedAt: [],
    createdBy: [],
    lastModifiedBy: [],
    tags: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected blogPostService: BlogPostService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ blogPost }) => {
      if (!blogPost.id) {
        const today = moment().startOf('day');
        blogPost.createdAt = today;
        blogPost.lastModifiedAt = today;
      }

      this.updateForm(blogPost);
    });
  }

  updateForm(blogPost: IBlogPost): void {
    this.editForm.patchValue({
      id: blogPost.id,
      title: blogPost.title,
      summary: blogPost.summary,
      figure: blogPost.figure,
      figureContentType: blogPost.figureContentType,
      content: blogPost.content,
      category: blogPost.category,
      slug: blogPost.slug,
      state: blogPost.state,
      weight: blogPost.weight,
      publishedAt: blogPost.publishedAt,
      createdAt: blogPost.createdAt ? blogPost.createdAt.format(DATE_TIME_FORMAT) : null,
      lastModifiedAt: blogPost.lastModifiedAt ? blogPost.lastModifiedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: blogPost.createdBy,
      lastModifiedBy: blogPost.lastModifiedBy,
      tags: blogPost.tags,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('sampleApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const blogPost = this.createFromForm();
    if (blogPost.id !== undefined) {
      this.subscribeToSaveResponse(this.blogPostService.update(blogPost));
    } else {
      this.subscribeToSaveResponse(this.blogPostService.create(blogPost));
    }
  }

  private createFromForm(): IBlogPost {
    return {
      ...new BlogPost(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      summary: this.editForm.get(['summary'])!.value,
      figureContentType: this.editForm.get(['figureContentType'])!.value,
      figure: this.editForm.get(['figure'])!.value,
      content: this.editForm.get(['content'])!.value,
      category: this.editForm.get(['category'])!.value,
      slug: this.editForm.get(['slug'])!.value,
      state: this.editForm.get(['state'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      publishedAt: this.editForm.get(['publishedAt'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      lastModifiedAt: this.editForm.get(['lastModifiedAt'])!.value
        ? moment(this.editForm.get(['lastModifiedAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      tags: this.editForm.get(['tags'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlogPost>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
