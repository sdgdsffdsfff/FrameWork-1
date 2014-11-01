package com.ray.app.chart.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.app.chart.entity.Chart;

@Component
public class ChartDao extends HibernateDao<Chart, String>
{
}
