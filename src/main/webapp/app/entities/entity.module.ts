import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'blog-post',
        loadChildren: () => import('./blog-post/blog-post.module').then(m => m.SampleAppBlogPostModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SampleAppEntityModule {}
