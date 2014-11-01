<chart showValue='1' bgAlpha='0' bgColor='FFFFFF' lowerLimit='0' upperLimit='100' numberSuffix='%25' showBorder='0' basefontColor='000000' chartTopMargin='0' chartBottomMargin='20' chartLeftMargin='0' chartRightMargin='0' toolTipBgColor='80A905' gaugeFillMix='{dark-10},FFFFFF,{dark-10}' gaugeFillRatio='3'>
   <colorRange>
      <color minValue='0' maxValue='45' code='8BBA00'/>
      <color minValue='45' maxValue='80' code='F6BD0F'/>
      <color minValue='80' maxValue='100' code='FF654F'/>
   </colorRange>

   <dials>
      <dial value='${data.datas.num}' rearExtension='10'/>
   </dials>

   <trendpoints>
      <point value='50' displayValue='Average' fontcolor='FF4400' useMarker='1' dashed='1' dashLen='2' dashGap='2' valueInside='1' />
   </trendpoints>

   <!--Rectangles behind the gauge -->
   <annotations>
      <annotationGroup id='Grp1' showBelow='1' >
         <annotation type='rectangle' radius='0' color='eeffee' showBorder='0' />
      </annotationGroup>
   </annotations>

   <styles>
      <definition>
         <style name='RectShadow' type='shadow' strength='3'/>
      </definition>
      <application>
         <apply toObject='Grp1' styles='RectShadow' />
      </application>
   </styles>
</chart>