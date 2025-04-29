import type { AppRouteRecordRaw } from '/@/router/types';
import { LAYOUT } from '/@/router/constant';

export const AI_ROUTE: AppRouteRecordRaw = {
  path: '',
  name: 'ai-parent',
  component: LAYOUT,
  meta: {
    title: 'ai',
  },
  children: [
    {
      path: '/ai',
      name: 'ai',
      component: () => import('/@/views/dashboard/ai/index.vue'),
      meta: {
        title: 'AI助手',
      },
    },
  ],
};

export const CONTROLL_ROUTE: AppRouteRecordRaw = {
  path: '',
  name: 'control',
  component: LAYOUT,
  meta: {
    title: 'control',
  },
  children: [
    {
      path: '/panel',
      name: 'panel',
      component: () => import('/@/views/qebot/ControlPanel.vue'),
      meta: {
        title: '控制面板',
      },
    },
  ],
};

export const staticRoutesList = [AI_ROUTE, CONTROLL_ROUTE];
